package com.example.gpspring.mvcframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class GPRequestMappingHandlerMapping implements GPHandlerMapping {
    public GPRequestMappingHandlerMapping() {
        initHandlerMethods();
    }

    private void initHandlerMethods() {
        methodRegistry.put("", new GPHandlerMethod());
    }

    private Map<String, GPHandlerMethod> methodRegistry = new HashMap<>();

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


        // 参数映射
        GPHandlerMethod handlerMethod = this.methodRegistry.get(url);

        return handlerMethod;
    }
}
