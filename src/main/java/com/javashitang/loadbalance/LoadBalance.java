package com.javashitang.loadbalance;

import java.util.List;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public interface LoadBalance {

    String selectService(List<String> serviceUrlList);
}
