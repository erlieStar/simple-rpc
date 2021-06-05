package com.javashitang.registry;

import java.net.InetSocketAddress;

/**
 * @author lilimin
 * @since 2020-09-15
 */
public interface RegistryService {

    /**
     * 注册服务
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 获取服务地址
     */
    InetSocketAddress lookup(String serviceName);
}
