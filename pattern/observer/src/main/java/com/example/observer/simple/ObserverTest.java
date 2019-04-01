package com.example.observer.simple;

public class ObserverTest {
    public static void main(String[] args) {
        Producer producer = Producer.getInstance();
        producer.addObserver(new Consumer());
        producer.produceMsg("hello, observer");
    }
}
