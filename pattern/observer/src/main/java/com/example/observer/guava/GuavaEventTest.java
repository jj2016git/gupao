package com.example.observer.guava;

import com.google.common.eventbus.EventBus;

public class GuavaEventTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventListener());
        eventBus.post("hello, first message");
    }
}
