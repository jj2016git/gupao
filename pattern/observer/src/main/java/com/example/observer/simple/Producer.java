package com.example.observer.simple;

import java.util.Observable;

public class Producer extends Observable {
    private static final Producer PRODUCER = new Producer();

    private Producer() {
    }

    public static Producer getInstance() {
        return PRODUCER;
    }

    public void produceMsg(String message) {
        System.out.println("producer start to produce message");

        this.setChanged();
        this.notifyObservers(message);
    }
}
