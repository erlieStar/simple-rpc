package com.javashitang;


import org.junit.Test;

import java.util.Random;

/**
 * @author lilimin
 * @since 2020-10-27
 */
public class MyTest {

    @Test
    public void test1() {
        Random random = new Random(2);
        System.out.println(random.nextInt());
    }
}
