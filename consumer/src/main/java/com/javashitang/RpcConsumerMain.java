package com.javashitang;

import com.javashitang.api.HelloService;
import com.javashitang.api.UpperCaseService;

public class RpcConsumerMain {

    public static void main(String[] args)  {

        // 因为这是一个小demo，就不拆分多模块了
        // 这个HelloService是通过网络调用的HelloServiceImpl，而不是本地调用
        HelloService helloService = ConsumerProxy.getProxy(HelloService.class, "127.0.0.1", 8080);
        // hello world
        System.out.println(helloService.sayHello("world"));
        UpperCaseService upperCaseService = ConsumerProxy.getProxy(UpperCaseService.class, "127.0.0.1", 8080);
        // THIS IS CONTENT
        System.out.println(upperCaseService.toUpperCase("this is content"));
    }
}
