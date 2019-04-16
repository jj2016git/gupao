package com.example.gpspring.mvcframework.context.support;

/**
 * IoC容器实现的顶层设计
 */
public abstract class GPAbstractApplicationContext {
    protected void refresh(){}

    public abstract String[] getBeanNames(Class<?> type);
}
