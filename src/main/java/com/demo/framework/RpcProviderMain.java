package com.demo.framework;

import com.demo.invoke.ProviderReflect;
import com.demo.service.HelloService;
import com.demo.service.HelloServiceImpl;

public class RpcProviderMain {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        ProviderReflect.provider(service, 8080);
    }
}
