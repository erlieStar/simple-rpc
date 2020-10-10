package com.javashitang.registry;

import java.net.InetSocketAddress;

/**
 * @author lilimin
 * @since 2020-09-15
 */
public interface RegistryService {

    void register(String serviceName, InetSocketAddress inetSocketAddress);

    String lookup(String serviceName);
}
