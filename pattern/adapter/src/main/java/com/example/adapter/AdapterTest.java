package com.example.adapter;

import com.example.adapter.thirdchannel.ILoginByThirdChannel;
import com.example.adapter.thirdchannel.ThirdChannelAdapter;

public class AdapterTest {
    public static void main(String[] args) {
        ILoginByThirdChannel login = new ThirdChannelAdapter();
        login.loginByQQ("aaa");
        login.loginByWechat("bbb");
        login.loginByRegister("tom", "123");
    }
}
