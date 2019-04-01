
package com.example.gpspring.demo.controller;

import com.example.gpspring.demo.service.IDemoService;
import com.example.gpspring.mvcframework.annotation.GPAutowired;
import com.example.gpspring.mvcframework.annotation.GPController;
import com.example.gpspring.mvcframework.annotation.GPRequestMapping;
import com.example.gpspring.mvcframework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@GPController
@GPRequestMapping(value = "/demo")
public class DemoController {
    @GPAutowired
    private IDemoService demoService;

    @GPRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp, @GPRequestParam("name") String name) {
        //		String result = demoService.get(name);
        String result = "My name is " + name;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GPRequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, @GPRequestParam("a") Integer a,
                    @GPRequestParam("b") Integer b) {
        try {
            resp.getWriter().write(a + "+" + b + "=" + (a + b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GPRequestMapping("/remove")
    public void remove(HttpServletRequest req, HttpServletResponse resp, @GPRequestParam("id") Integer id) {
    }
}
