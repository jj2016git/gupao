package com.example.decorator;

public interface ISigninService {
    RespMsg login(String username, String password);

    RespMsg register(String username, String password);
}
