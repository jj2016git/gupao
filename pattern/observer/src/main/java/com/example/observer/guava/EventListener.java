package com.example.observer.guava;

import com.google.common.eventbus.Subscribe;

public class EventListener {
    @Subscribe
    public void subscribe(String str) {
        System.out.printf("接收到消息：" + str);
    }
}
