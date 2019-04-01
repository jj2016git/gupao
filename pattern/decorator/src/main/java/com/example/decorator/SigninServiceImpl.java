package com.example.decorator;

public class SigninServiceImpl implements ISigninService {
    @Override
    public RespMsg login(String username, String password) {
        System.out.println("通过用户名、密码登录");
        return new RespMsg(1000, "成功");
    }

    @Override
    public RespMsg register(String username, String password) {
        System.out.println("通过用户名、密码注册");
        return new RespMsg(1000, "成功");
    }
}
