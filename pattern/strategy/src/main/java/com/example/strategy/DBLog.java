package com.example.strategy;

public class DBLog implements ILog {
    @Override
    public void log() {
        System.out.println("打印log至数据库");
    }
}
