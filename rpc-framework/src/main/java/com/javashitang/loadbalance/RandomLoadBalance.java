package com.javashitang.loadbalance;

import com.javashitang.tool.JsonConvert;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author lilimin
 * @since 2020-09-22
 */
@Slf4j
public class RandomLoadBalance extends AbstractLoadBalance {

    private final Random random = new Random();

    @Override
    public String doSelect(List<String> serviceUrlList) {
        log.info("doSelect param serviceUrlList: {}", JsonConvert.obj2Str(serviceUrlList));
        return serviceUrlList.get(random.nextInt(serviceUrlList.size()));
    }
}
