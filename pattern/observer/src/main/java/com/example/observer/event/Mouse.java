package com.example.observer.event;

import static com.example.observer.event.EventType.ON_BLUR;
import static com.example.observer.event.EventType.ON_CLICK;

public class Mouse extends EventEmitter {

    public void click() {
        trigger(ON_CLICK);
    }

    public void blur() {
        trigger(ON_BLUR);
    }
}
