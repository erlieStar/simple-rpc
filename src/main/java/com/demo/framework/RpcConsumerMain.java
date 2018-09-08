package com.demo.framework;

import com.demo.invoke.ConsumerProxy;
import com.demo.service.HelloService;

public class RpcConsumerMain {

    public static void main(String[] args)  {

        HelloService service = ConsumerProxy.consume(HelloService.class, "127.0.0.1", 8080);
        for (int i = 0; i < 10; i++) {
            String msg = service.sayHello("world " + i);
            // hello world 0
            // hello world 1
            System.out.println(msg);
        }
    }
}
