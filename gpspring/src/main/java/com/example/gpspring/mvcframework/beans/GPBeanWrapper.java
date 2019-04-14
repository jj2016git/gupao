package com.example.gpspring.mvcframework.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GPBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;
}
