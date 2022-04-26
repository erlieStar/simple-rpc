package com.javashitang.service;


import com.javashitang.api.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String content) {
        return "hello " + content;
    }
}
