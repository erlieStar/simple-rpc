package com.javashitang.service;

import com.google.common.collect.Maps;
import com.javashitang.registry.RegistryService;
import com.javashitang.registry.ZookeeperRegistryService;
import com.javashitang.remoting.transport.NettyServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public class ServiceMap {

    private static Map<String, Object> serviceMap = Maps.newHashMap();
    private static final RegistryService registryService = new ZookeeperRegistryService();

    public static void registryService(String serviceKey, Object object) {
        try {
            serviceMap.put(serviceKey, object);
            String host = InetAddress.getLocalHost().getHostAddress();
            InetSocketAddress address = new InetSocketAddress(host, NettyServer.port);
            registryService.register(serviceKey, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getService(String serviceKey) {
        return serviceMap.get(serviceKey);
    }
}
