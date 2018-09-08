package com.demo.service;

public class Test {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        String msg = helloService.sayHello("world");
        // hello world
        System.out.println(msg);
    }
}
