package com.javashitang.framework;

import com.javashitang.invoke.ConsumerProxy;
import com.javashitang.service.HelloService;

public class RpcConsumerMain {

    public static void main(String[] args)  {

        HelloService service = ConsumerProxy.getProxy(HelloService.class, "127.0.0.1", 8080);
        for (int i = 0; i < 3; i++) {
            String msg = service.sayHello("world " + i);
            // hello world 0
            // hello world 1
            // hello world 2
            System.out.println(msg);
        }
    }
}
