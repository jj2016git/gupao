package com.example.adapter.thirdchannel;

import com.example.adapter.old.RespMsg;
import com.example.adapter.old.SigninServiceImpl;

public class ThirdChannelAdapter extends SigninServiceImpl implements ILoginByThirdChannel {
    @Override
    public RespMsg loginByQQ(String openId) {
        return processLogin(openId, QQLoginAdapter.class);
    }

    @Override
    public RespMsg loginByWechat(String openId) {
        return processLogin(openId, WeChatLoginAdapter.class);
    }

    private RespMsg processLogin(String id, Class<? extends LoginAdapter> clazz) {
        try {
            LoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)) {
                return adapter.login(id);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RespMsg loginByRegister(String username, String password) {
        super.register(username, password);
        return super.login(username, password);
    }
}
