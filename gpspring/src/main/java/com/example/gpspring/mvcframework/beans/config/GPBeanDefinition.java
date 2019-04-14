package com.example.gpspring.mvcframework.beans.config;

import lombok.Data;

@Data
public class GPBeanDefinition {
    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;

    public GPBeanDefinition(String factoryBeanName, String beanClassName) {
        this.beanClassName = beanClassName;
        this.factoryBeanName = factoryBeanName;
    }
}
