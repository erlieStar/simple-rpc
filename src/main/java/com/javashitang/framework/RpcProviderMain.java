package com.javashitang.framework;

import com.javashitang.invoke.ProviderReflect;
import com.javashitang.service.HelloService;
import com.javashitang.service.HelloServiceImpl;

public class RpcProviderMain {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        ProviderReflect.invoke(service, 8080);
    }
}
