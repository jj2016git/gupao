package com.example.pattern.proxy;

public interface IOrderService {
    Integer createOrder();

    Order queryOrder();

    Integer deleteOrder();
}
