package com.javashitang.registry;

import java.net.InetSocketAddress;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class ZookeeperRegistryService implements RegistryService {

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {

    }

    @Override
    public String lookup(String serviceName) {
        return null;
    }
}
