package com.example.pattern.singleton.register;

public enum EnumSingleton {
    INSTANCE("singleton");

    private String name;

    EnumSingleton(String name) {
        this.name = name;
    }

    EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
