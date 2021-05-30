package com.javashitang;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author lilimin
 * @since 2021-05-30
 */
public class ServerSpiDemo {

    public static void main(String[] args) {
        RemoteServer rpcAccessPoint = StreamSupport.
                stream(ServiceLoader.load(RemoteServer.class).spliterator(), false)
                .findFirst().orElse(null);
        System.out.println(rpcAccessPoint);
    }
}
