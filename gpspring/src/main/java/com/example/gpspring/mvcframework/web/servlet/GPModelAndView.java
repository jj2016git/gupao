package com.example.gpspring.mvcframework.web.servlet;

import lombok.Data;

import java.util.Map;

@Data
public class GPModelAndView {
    /** View instance or view name String */
    private Object view;

    /** Model Map */
    private Map<String, Object> model;
}
