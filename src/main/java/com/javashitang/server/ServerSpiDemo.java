package com.javashitang.server;

import com.javashitang.util.SpiUtil;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author lilimin
 * @since 2021-05-30
 */
public class ServerSpiDemo {

    /**
     * 会先加载自己包的实现，再加载其他包的实现
     */
    public static void main(String[] args) {
        RemoteServer remoteServer = StreamSupport.
                stream(ServiceLoader.load(RemoteServer.class).spliterator(), false)
                .findFirst().orElse(null);
        System.out.println(remoteServer);

        RemoteServer server = SpiUtil.load(RemoteServer.class);
        System.out.println(server);
    }
}
