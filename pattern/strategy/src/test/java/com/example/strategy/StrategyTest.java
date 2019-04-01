package com.example.strategy;

public class StrategyTest {
    public static void main(String[] args) {
        String logType = "file";
        ILog logStrategy = LogFactory.getInstance().getLogger(logType);
        logStrategy.log();
    }
}
