package com.example.gpspring.mvcframework.web.servlet;

import com.example.gpspring.mvcframework.annotation.GPRequestParam;
import com.example.gpspring.mvcframework.beans.GPBeanFactory;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Data
public class GPHandlerMethod {
    private Object bean;

    private GPBeanFactory beanFactory;

    private Class<?> beanType;

    private Method method;

    private Class<?>[] paramTypes;

    /**
     * 请求参数名->方法参数位置
     */
    private Map<String, Integer> paramIndexMapping = new HashMap<>();

    private void initParamIndexMapping() {
        Parameter[] parameters = method.getParameters();
        this.paramTypes = method.getParameterTypes();
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
