package com.example.gpspring.mvcframework.context;

@FunctionalInterface
public interface GPObjectFactory<T> {
    /**
     * Return an instance (possibly shared or independent)
     * of the object managed by this factory.
     * @return the resulting instance
     * @throws Exception in case of creation errors
     */
    T getObject() throws Exception;
}
