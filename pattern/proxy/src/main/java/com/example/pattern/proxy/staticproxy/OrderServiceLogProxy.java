package com.example.pattern.proxy.staticproxy;

import com.example.pattern.proxy.IOrderService;
import com.example.pattern.proxy.Order;

public class OrderServiceLogProxy implements IOrderService {
    private IOrderService orderService;

    public OrderServiceLogProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Integer createOrder() {
        before("createOrder");
        int ret = orderService.createOrder();
        after("createOrder");
        return ret;
    }

    @Override
    public Order queryOrder() {
        before("queryOrder");
        Order order = orderService.queryOrder();
        after("queryOrder");
        return order;
    }

    @Override
    public Integer deleteOrder() {
        before("deleteOrder");
        int ret = orderService.deleteOrder();
        after("deleteOrder");
        return ret;
    }

    private void before(String methodName) {
        System.out.println("static proxy: keep record before " + methodName);
    }

    private void after(String methodName) {
        System.out.println("static proxy: keep record after " + methodName);
    }
}
