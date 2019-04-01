package com.example.decorator;

/**
 * 新接口
 */
public interface ILoginByThirdChannel extends ISigninService {
    RespMsg loginByQQ(String openId);

    RespMsg loginByWechat(String openId);

    RespMsg loginByRegister(String username, String password);
}
