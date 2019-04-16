package com.example.gpspring.mvcframework.web.servlet;

import com.example.gpspring.mvcframework.annotation.GPController;
import com.example.gpspring.mvcframework.annotation.GPRequestMapping;
import com.example.gpspring.mvcframework.context.GPApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GPRequestMappingHandlerMapping implements GPHandlerMapping {

    private final GPApplicationContext context;
    private Map<String, GPHandlerMethod> urlHandlerMapping = new HashMap<>();

    public GPRequestMappingHandlerMapping(GPApplicationContext context) {
        this.context = context;
        initHandlerMethods();
    }

    /**
     * 建立url与method的映射关系
     */
    private void initHandlerMethods() {
        String[] beanNames = context.getBeanNames(Object.class);

        for (String beanName : beanNames){
            Object bean = null;
            try {
                bean = context.getBean(beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Class<?> type = bean.getClass();
            if (type.isAnnotationPresent(GPController.class) || type.isAnnotationPresent(GPRequestMapping.class)) {
                String baseUrl = "";
                if (type.isAnnotationPresent(GPController.class)) {
                    GPRequestMapping requestMapping = type.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                Method[] methods = type.getMethods();
                for (Method method : methods) {
                    String methodUrl = "";
                    if (method.isAnnotationPresent(GPRequestMapping.class)) {
                        GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                        methodUrl = requestMapping.value();
                    }
                    urlHandlerMapping.putIfAbsent(baseUrl + methodUrl, new GPHandlerMethod(bean, type, method));
                }
            }
        }
        System.out.println(urlHandlerMapping);
    }


    /**
     * 取controller及方法 @RequestMapping注解的value, 拼接成url，与请求的url进行匹配，返回匹配的方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public GPHandlerMethod getHandler(HttpServletRequest request) throws Exception {
        // get request url
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        return this.urlHandlerMapping.get(url);
    }
}
