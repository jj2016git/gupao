package com.example.gpspring.demo.service.impl;

import com.example.gpspring.demo.service.IDemoService;
import com.example.gpspring.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
