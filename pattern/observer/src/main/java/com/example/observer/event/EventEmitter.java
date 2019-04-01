package com.example.observer.event;


import java.util.HashMap;
import java.util.Map;

public class EventEmitter {
    Map<EventType, Event> events = new HashMap<>();

    /**
     * 根据事件类型生成事件
     *
     * @param eventType
     */
    public void trigger(EventType eventType) {
        // 1. 生成事件
        Event event = events.get(eventType);
        if (event == null) {
            return;
        }
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        event.setTrigger(eventType.name());

        // 2. 触发回调
        trigger(event);
    }

    private void trigger(Event event) {
        try {
            event.getCallback().invoke(event.getTarget(), event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListener(EventType eventType, MouseEventCallback callback) {
        try {
            events.put(eventType, new Event(callback, callback.getClass().getMethod(getMethodByEventType(eventType), Event.class)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private String getMethodByEventType(EventType eventType) {
        String name = eventType.name();
        String[] array = name.split("_");
        return array[0].toLowerCase() + array[1].charAt(0) + array[1].substring(1).toLowerCase();
    }
}
