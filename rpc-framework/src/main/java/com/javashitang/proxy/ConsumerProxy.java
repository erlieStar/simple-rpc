package com.javashitang.proxy;

import java.lang.reflect.Proxy;

/**
 * @author lilimin
 * @since 2020-10-25
 */
public class ConsumerProxy {

    public static <T> T getProxy(final Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new ConsumerInvocationHandler());
    }
}
