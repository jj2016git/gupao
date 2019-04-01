package com.example.adapter.thirdchannel;

import com.example.adapter.old.RespMsg;

public class QQLoginAdapter implements LoginAdapter {
    @Override
    public RespMsg login(String openId) {
        System.out.println("login by QQ");
        return null;
    }


    @Override
    public boolean support(LoginAdapter adapter) {
        return adapter instanceof QQLoginAdapter;
    }
}
