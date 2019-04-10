package com.example.gpspring.mvcframework.beans.support;

import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;
import com.example.gpspring.mvcframework.context.support.GPAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {
    private final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
}
