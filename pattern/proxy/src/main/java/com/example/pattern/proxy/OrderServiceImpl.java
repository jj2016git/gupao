package com.example.pattern.proxy;

import java.util.Date;

public class OrderServiceImpl implements IOrderService {
    @Override
    public Integer createOrder() {
        System.out.println("create order");
        return 0;
    }

    @Override
    public Order queryOrder() {
        System.out.println("query order");
        return new Order("traceId", "orderName", new Date(), 100);
    }

    @Override
    public Integer deleteOrder() {
        System.out.println("delete order");
        return 0;
    }
}
