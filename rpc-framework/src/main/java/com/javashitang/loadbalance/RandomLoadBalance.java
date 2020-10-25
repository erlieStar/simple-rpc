package com.javashitang.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    public String doSelect(List<String> serviceUrlList) {
        Random random = new Random(serviceUrlList.size());
        return serviceUrlList.get(random.nextInt());
    }
}
