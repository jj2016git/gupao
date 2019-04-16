package com.example.gpspring.demo.service.impl;

import com.example.gpspring.mvcframework.annotation.GPAutowired;
import com.example.gpspring.mvcframework.annotation.GPService;

@GPService
public class BService {
    @GPAutowired
    private AService aService;
}
