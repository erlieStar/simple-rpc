package com.javashitang;

import com.javashitang.api.HelloService;
import com.javashitang.api.UpperCaseService;

public class RpcConsumerMain {

    public static void main(String[] args)  {

        HelloService helloService = ConsumerProxy.getProxy(HelloService.class, "127.0.0.1", 8080);
        System.out.println(helloService.sayHello("world"));
        UpperCaseService upperCaseService = ConsumerProxy.getProxy(UpperCaseService.class, "127.0.0.1", 8080);
        System.out.println(upperCaseService.toUpperCase("this is content"));
    }
}
