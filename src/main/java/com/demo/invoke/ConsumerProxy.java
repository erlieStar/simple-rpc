package com.demo.invoke;

import java.lang.reflect.Proxy;

public class ConsumerProxy {

    public static <T> T consume(final Class<T> interfaceClass, final String host, final int port) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new ConsumerInvocationHandler(host, port));
    }
}
