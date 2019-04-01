package com.example.decorator;

public class ThirdChannelLogin implements ILoginByThirdChannel {
    private ISigninService iSigninService;

    public ThirdChannelLogin(ISigninService iSigninService) {
        this.iSigninService = iSigninService;
    }

    @Override
    public RespMsg loginByQQ(String openId) {
        return null;
    }

    @Override
    public RespMsg loginByWechat(String openId) {
        return null;
    }

    @Override
    public RespMsg loginByRegister(String username, String password) {
        return null;
    }

    @Override
    public RespMsg login(String username, String password) {
        return null;
    }

    @Override
    public RespMsg register(String username, String password) {
        iSigninService.register(username, password);
        return iSigninService.login(username, password);
    }
}
