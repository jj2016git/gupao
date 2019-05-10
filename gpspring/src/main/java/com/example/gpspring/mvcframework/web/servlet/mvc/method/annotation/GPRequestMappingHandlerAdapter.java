package com.example.gpspring.mvcframework.web.servlet.mvc.method.annotation;

import com.example.gpspring.mvcframework.web.servlet.GPHandlerAdapter;
import com.example.gpspring.mvcframework.web.servlet.GPHandlerMethod;
import com.example.gpspring.mvcframework.web.servlet.GPModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static jdk.nashorn.api.scripting.ScriptUtils.convert;

public class GPRequestMappingHandlerAdapter implements GPHandlerAdapter {
    /**
     * Given a handler instance, return whether or not this {@code HandlerAdapter}
     * can support it. Typical HandlerAdapters will base the decision on the handler
     * type. HandlerAdapters will usually only support one handler type each.
     * <p>A typical implementation:
     * <p>{@code
     * return (handler instanceof MyHandler);
     * }
     *
     * @param handler handler object to check
     * @return whether or not this object can use the given handler
     */
    @Override
    public boolean supports(Object handler) {
        return handler instanceof GPHandlerMethod;
    }

    /**
     * Use the given handler to handle this request.
     * The workflow that is required may vary widely.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  handler to use. This object must have previously been passed
     *                 to the {@code supports} method of this interface, which must have
     *                 returned {@code true}.
     * @return ModelAndView object with the name of the view and the required
     * model data, or {@code null} if the request has been handled directly
     * @throws Exception in case of errors
     */
    @Override
    public GPModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 请求参数转换成方法参数
        // 2. 方法调用
        GPHandlerMethod handlerMethod = (GPHandlerMethod) handler;
        Map<String, String[]> requestParams = request.getParameterMap();
        Object[] args = getMethodArgsFromRequestParams(handlerMethod, requestParams, request, response);

        Object result = handlerMethod.getMethod().invoke(handlerMethod.getBean(), args);
        if (result instanceof GPModelAndView) {
            return (GPModelAndView) result;
        }
        return null;
    }

    private Object[] getMethodArgsFromRequestParams(GPHandlerMethod handler, Map<String, String[]> requestParams, HttpServletRequest request, HttpServletResponse response) {
        // 请求参数名->方法参数index
        Map<String, Integer> paramIndex = handler.getParamIndexMapping();
        Class<?>[] paramTypes = handler.getParamTypes();
        Object[] args = new Object[paramIndex.size()];
        for (Map.Entry<String, Integer> entry : paramIndex.entrySet()) {
            Integer index = entry.getValue();
            if (entry.getKey().equals(HttpServletRequest.class.getName())) {
                args[index] = request;
            } else if (entry.getKey().equals(HttpServletResponse.class.getName())) {
                args[index] = response;
            } else {
                args[index] = convert(String.join(",", requestParams.get(entry.getKey())), paramTypes[index]);
            }
        }
        return args;
    }
}
