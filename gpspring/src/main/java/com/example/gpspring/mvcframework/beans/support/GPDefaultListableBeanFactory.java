package com.example.gpspring.mvcframework.beans.support;

import com.example.gpspring.mvcframework.beans.GPBeanFactory;
import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class GPDefaultListableBeanFactory implements GPBeanFactory {
    private final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
