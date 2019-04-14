package com.example.gpspring.mvcframework.beans.config;

import lombok.Data;

@Data
public class GPBeanDefinition {
    private String beanClassName;
    private boolean lazyInit = false;
    private Class<?> beanClass;
//    private String factoryBeanName;

    public GPBeanDefinition(String beanClassName, Class<?> beanClass) {
        this.beanClassName = beanClassName;
        this.beanClass = beanClass;
    }
}
