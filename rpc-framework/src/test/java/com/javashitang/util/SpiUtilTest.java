package com.javashitang.util;

import com.javashitang.registry.RegistryService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lilimin
 * @since 2021-05-30
 */
public class SpiUtilTest {

    @Test
    public void load() {
        RegistryService service = SpiUtil.load(RegistryService.class);
        System.out.println(service);
    }
}