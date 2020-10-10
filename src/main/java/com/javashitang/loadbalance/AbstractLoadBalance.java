package com.javashitang.loadbalance;

import java.util.List;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectService(List<String> serviceUrlList) {
        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }
        return doSelect(serviceUrlList);
    }

    public abstract String doSelect(List<String> serviceUrlList);
}
