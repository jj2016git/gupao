package com.example.adapter.thirdchannel;

import com.example.adapter.old.ISigninService;
import com.example.adapter.old.RespMsg;

/**
 * 新接口
 */
public interface ILoginByThirdChannel extends ISigninService {
    RespMsg loginByQQ(String openId);

    RespMsg loginByWechat(String openId);

    RespMsg loginByRegister(String username, String password);
}
