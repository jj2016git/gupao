package com.example.gpspring.mvcframework.web.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class GPModelAndView {
    private String viewName;
    private Map<String,?> model;

    public GPModelAndView(String viewName) {
        this.viewName = viewName;
    }
}
