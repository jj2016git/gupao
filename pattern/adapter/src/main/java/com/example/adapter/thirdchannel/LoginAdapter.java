package com.example.adapter.thirdchannel;

import com.example.adapter.old.RespMsg;

public interface LoginAdapter {
    boolean support(LoginAdapter adapter);

    RespMsg login(String id);
}
