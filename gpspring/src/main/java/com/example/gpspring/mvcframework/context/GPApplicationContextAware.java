package com.example.gpspring.mvcframework.context;

/**
 * 通过listener扫描实现该接口的所有类，将IoC容器自动注入类中
 */
public interface GPApplicationContextAware {
    void setApplicationContext(GPApplicationContext applicationContext);
}
