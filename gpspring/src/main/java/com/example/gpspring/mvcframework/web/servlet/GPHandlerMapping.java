package com.example.gpspring.mvcframework.web.servlet;

import javax.servlet.http.HttpServletRequest;

public interface GPHandlerMapping {
    Object getHandler(HttpServletRequest request) throws Exception;
}
