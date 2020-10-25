package com.javashitang.registry;

import com.javashitang.exception.RpcException;
import com.javashitang.loadbalance.LoadBalance;
import com.javashitang.loadbalance.RandomLoadBalance;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class ZookeeperRegistryService implements RegistryService {

    private final LoadBalance loadBalance = new RandomLoadBalance();

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        CuratorFramework zkClient = CuratorZkUtil.getZkClient();
        CuratorZkUtil.createPersistentMode(zkClient, serviceName + inetSocketAddress.toString());
    }

    @Override
    public InetSocketAddress lookup(String serviceName) {
        CuratorFramework zkClient = CuratorZkUtil.getZkClient();
        List<String> serviceUrls = CuratorZkUtil.getChildrenNodes(zkClient, serviceName);
        if (CollectionUtils.isEmpty(serviceUrls)) {
            throw new RpcException("");
        }
        String targetServiceUrl = loadBalance.selectService(serviceUrls);
        String[] array = targetServiceUrl.split(":");
        return new InetSocketAddress(array[0], Integer.valueOf(array[1]));
    }
}
