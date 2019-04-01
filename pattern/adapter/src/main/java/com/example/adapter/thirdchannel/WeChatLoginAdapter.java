package com.example.adapter.thirdchannel;

import com.example.adapter.old.RespMsg;

public class WeChatLoginAdapter implements LoginAdapter {
    @Override
    public boolean support(LoginAdapter adapter) {
        return adapter instanceof WeChatLoginAdapter;
    }

    @Override
    public RespMsg login(String id) {
        System.out.println("login by wechat");
        return null;
    }
}
