package com.javashitang.util;

import com.javashitang.loadbalance.LoadBalance;
import com.javashitang.registry.RegistryService;
import org.junit.Test;

import java.util.ServiceLoader;

import static org.junit.Assert.*;

/**
 * @author lilimin
 * @since 2021-05-30
 */
public class SpiUtilTest {

    @Test
    public void load() {
        LoadBalance loadBalance = SpiUtil.load(LoadBalance.class);
        System.out.println(loadBalance);
        System.out.println("---");
        ServiceLoader<LoadBalance> loadBalances = ServiceLoader.load(LoadBalance.class);
        loadBalances.forEach(item -> System.out.println(item));
    }
}