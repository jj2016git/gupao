package com.example.gpspring.mvcframework.web.servlet.mvc.method.annotation;

import com.example.gpspring.mvcframework.web.servlet.GPHandlerAdapter;
import com.example.gpspring.mvcframework.web.servlet.GPHandlerMethod;
import com.example.gpspring.mvcframework.web.servlet.GPModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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
        Object[] args = null;

        // 2. 方法调用
        GPHandlerMethod handlerMethod = (GPHandlerMethod) handler;
        Object result = handlerMethod.getMethod().invoke(handlerMethod.getBean(), args);
        if (result instanceof GPModelAndView) {
            return (GPModelAndView) result;
        }

        return null;
    }
}
