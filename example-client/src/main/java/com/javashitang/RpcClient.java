package com.javashitang;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RpcClient {

    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcClient.class);
    }
}
