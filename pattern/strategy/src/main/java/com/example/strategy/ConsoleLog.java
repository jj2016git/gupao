package com.example.strategy;

public class ConsoleLog implements ILog {
    @Override
    public void log() {
        System.out.println("打印日志至控制台");
    }
}
