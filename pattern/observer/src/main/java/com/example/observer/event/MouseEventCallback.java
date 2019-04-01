package com.example.observer.event;

public class MouseEventCallback {
    public void onClick(Event e) {
        System.out.println("触发鼠标点击事件");
        System.out.println("event = " + e);
    }

    public void onBlur(Event e) {
        System.out.println("触发鼠标失焦事件");
        System.out.println("event = " + e);
    }

    public void onUp(Event e) {
        System.out.println("触发鼠标抬起事件");
        System.out.println("event = " + e);
    }

    public void onDown(Event e) {
        System.out.println("触发鼠标按下事件");
        System.out.println("event = " + e);
    }
}
