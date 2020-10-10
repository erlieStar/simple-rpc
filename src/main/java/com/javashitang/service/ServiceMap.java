package com.javashitang.service;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public class ServiceMap {

    private static Map<String, Object> serviceMap = Maps.newHashMap();

    public static void registryService(String serviceKey, Class<?> clazz) {

    }

    public static Object getService(String serviceKey) {
        return serviceMap.get(serviceKey);
    }
}
