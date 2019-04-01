package com.example.strategy;

public class FileLog implements ILog {
    @Override
    public void log() {
        System.out.println("打印log至日志文件");
    }
}
