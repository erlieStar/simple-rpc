package com.javashitang.loadbalance;

import java.util.List;

/**
 * @author lilimin
 * @since 2021-05-31
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    private Integer index = 0;

    @Override
    public String doSelect(List<String> serviceUrlList) {
        index = (index + 1) % serviceUrlList.size();
        return serviceUrlList.get(index);
    }
}
