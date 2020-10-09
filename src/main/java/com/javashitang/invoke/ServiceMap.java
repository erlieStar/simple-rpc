package com.javashitang.invoke;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lilimin
 * @since 2020-10-09
 */
public class ServiceMap {

    private static Map<String, Object> serviceMap = new HashMap<>();

    public static void putService(String serviceKey, Object service) {
        serviceMap.put(serviceKey, service);
    }

    public static Object getService(String serviceKey) {
        return serviceMap.get(serviceKey);
    }
}
