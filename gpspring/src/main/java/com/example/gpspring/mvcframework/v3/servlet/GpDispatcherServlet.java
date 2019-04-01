package com.example.gpspring.mvcframework.v3.servlet;

import com.example.gpspring.mvcframework.annotation.GPAutowired;
import com.example.gpspring.mvcframework.annotation.GPController;
import com.example.gpspring.mvcframework.annotation.GPRequestMapping;
import com.example.gpspring.mvcframework.annotation.GPRequestParam;
import com.example.gpspring.mvcframework.annotation.GPService;
import lombok.Data;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import static jdk.nashorn.api.scripting.ScriptUtils.convert;


public class GpDispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();
    private List<String> classNames = new ArrayList<>();
    private Map<String, Method> handlerMapper = new HashMap<>();
    private Map<String, Object> iocContainer = new HashMap<>();
    private List<Handler> urlHandlers = new ArrayList<>();

    /**
     * DispatcherServlet初始化流程：
     * 1. 根据contextConfigLocation加载配置文件
     * 2. IOC容器初始化
     * (1) 扫描包
     * (2) 初始化实例，将实例放入IOC容器内
     * 3. DI
     * 4. 建立url与Controller method的映射关系
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        instantiateBeans(contextConfig.getProperty("scanPackage"));

        doInjectDependencies();

        initHandlerMappings();

        System.out.println("GPSpring framework is initialized");
    }

    private void initHandlerMappings() {
        for (Map.Entry<String, Object> entry : iocContainer.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(GPController.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                String baseUrl = "";
                if (requestMapping != null) {
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(GPRequestMapping.class)) {
                        String methodUrl = ("/" + baseUrl + "/" + method.getAnnotation(GPRequestMapping.class).value())
                                .replaceAll("/+", "/");
                        urlHandlers.add(new Handler(Pattern.compile(methodUrl), entry.getValue(), method));
                    }
                }
            }
        }
        System.out.println(handlerMapper);
    }

    private void doInjectDependencies() {
        try {
            for (Map.Entry<String, Object> entry : iocContainer.entrySet()) {
                Object object = entry.getValue();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(GPAutowired.class)) {
                        GPAutowired gpAutowired = field.getAnnotation(GPAutowired.class);
                        String beanName = gpAutowired.value().trim();

                        // 按照类型注入
                        if ("".equals(beanName)) {
                            beanName = field.getType().getName();
                        }

                        field.setAccessible(true);
                        field.set(entry.getValue(), iocContainer.get(beanName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void instantiateBeans(String scanPackage) {
        doScan(scanPackage);

        doInstantiation();
    }

    private void doInstantiation() {
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(GPController.class)) {
                    Object instance = clazz.newInstance();
                    String beanName = lowerFirst(clazz.getSimpleName());
                    iocContainer.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    // 1. 处理@GPService自定义value的情况
                    GPService gpService = clazz.getAnnotation(GPService.class);
                    String beanName = gpService.value();

                    // 2. 默认beanName为类名首字母小写
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirst(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    iocContainer.put(beanName, instance);

                    // 3. 根据类型自动赋值
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (iocContainer.containsKey(i.getName())) {
                            throw new RuntimeException(String.format("the %s has been instantiated", i.getName()));
                        }
                        iocContainer.put(i.getName(), instance);
                    }
                }
            }
            System.out.println(iocContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String lowerFirst(String simpleName) {
        char[] tmpArr = simpleName.toCharArray();
        if (tmpArr[0] >= 'A' && tmpArr[0] <= 'Z') {
            tmpArr[0] += 32;
        }

        return String.valueOf(tmpArr);
    }

    private void doScan(String scanPackage) {
        // target/classes/com/example/gpspring/demo
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        System.err.println(url.getPath());

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                if (file.getName().endsWith(".class")) {
                    String className = scanPackage + "." + file.getName().replace(".class", "");
                    classNames.add(className);
                }
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation)) {
            contextConfig.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = request.getRequestURI();
            String contextPath = request.getContextPath();
            System.out.println("contextPath=" + contextPath);
            url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

            Handler urlHandler = findHandler(url);
            if (urlHandler == null) {
                response.getWriter().write("404 not found");
                return;
            }

            Map<String, String[]> requestParams = request.getParameterMap();
            Object[] args = getMethodArgsFromRequestParams(urlHandler, requestParams, request, response);
            urlHandler.getMethod().invoke(urlHandler.getController(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler findHandler(String url) {
        for (Handler urlHandler : urlHandlers) {
            if (urlHandler.getPattern().matcher(url).matches()) {
                return urlHandler;
            }
        }
        return null;
    }

    private Object[] getMethodArgsFromRequestParams(Handler handler, Map<String, String[]> requestParams,
                                                    HttpServletRequest request, HttpServletResponse response) {
        Class<?>[] paramTypes = handler.getParamTypes();
        Object[] args = new Object[paramTypes.length];
        Map<String, Integer> paramIndexMapping = handler.getParamIndexMapping();

        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String arg = String.join(",", requestParams.get(entry.getKey()));
            Integer index = paramIndexMapping.get(entry.getKey());
            args[index] = convert(arg, paramTypes[index]);
        }

        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            Integer index = paramIndexMapping.get(HttpServletRequest.class.getName());
            args[index] = request;
        }

        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            Integer index = paramIndexMapping.get(HttpServletResponse.class.getName());
            args[index] = response;
        }
        return args;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }

    @Data
    private static class Handler {
        /**
         * url pattern，支持正则匹配
         */
        private Pattern pattern;
        /**
         * url对应的controller方法
         */
        private Method method;
        /**
         * url对应的controller对象
         */
        private Object controller;
        private Class<?>[] paramTypes;
        /**
         * 请求参数名->方法参数位置
         */
        private Map<String, Integer> paramIndexMapping = new HashMap<>();


        public Handler(Pattern pattern, Object controller, Method method) {
            this.pattern = pattern;
            this.method = method;
            this.controller = controller;
            initParamIndexMapping();
        }

        private void initParamIndexMapping() {
            this.paramTypes = method.getParameterTypes();
            Parameter[] parameters = method.getParameters();
            for (int index = 0; index < parameters.length; index++) {
                if (parameters[index].isAnnotationPresent(GPRequestParam.class)) {
                    this.paramIndexMapping.put(parameters[index].getAnnotation(GPRequestParam.class).value(), index);
                } else if (parameters[index].getType() == HttpServletRequest.class) {
                    this.paramIndexMapping.put(HttpServletRequest.class.getName(), index);
                } else if (parameters[index].getType() == HttpServletResponse.class) {
                    this.paramIndexMapping.put(HttpServletResponse.class.getName(), index);
                }
            }
        }
    }

}


