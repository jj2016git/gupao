package com.example.observer.gperadvice;

import com.google.common.eventbus.EventBus;

public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new Teacher("tom"));
        eventBus.post(new Question("xiaoming", "guava怎么实现listener", "GP社区"));
    }
}
