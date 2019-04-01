package com.example.observer.event;

import javax.swing.*;

public class EventListenerTest {
    public static void main(String[] args) {
        // 事件源JButton，事件，监听器，回调
        // 0. 事件源绑定监听器/回调方法
        // 1. 外部动作作用于事件源
        // 2. 事件源产生事件
        // 3. 通知监听器，触发回调
        JButton button = new JButton();
        button.addActionListener(System.out::println);
        button.doClick();

        // 同理：Mouse作为事件源
        MouseEventCallback callback = new MouseEventCallback();

        Mouse mouse = new Mouse();

        //@谁？  @回调方法
        mouse.addListener(EventType.ON_CLICK, callback);
        mouse.addListener(EventType.ON_BLUR, callback);

        mouse.click();

        mouse.blur();
    }
}
