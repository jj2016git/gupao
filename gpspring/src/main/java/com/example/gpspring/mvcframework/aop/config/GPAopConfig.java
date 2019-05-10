package com.example.gpspring.mvcframework.aop.config;

import lombok.Data;

@Data
public class GPAopConfig {
    private String pointCut;
    private String aspectClass;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
