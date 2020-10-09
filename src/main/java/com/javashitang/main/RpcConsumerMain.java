package com.javashitang.main;

import com.javashitang.invoke.ConsumerProxy;
import com.javashitang.service.HelloService;

public class RpcConsumerMain {

    public static void main(String[] args)  {

        // 因为这是一个小demo，就不拆分多模块了
        // 这个HelloService是通过网络调用的HelloServiceImpl，而不是本地调用
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
