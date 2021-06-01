package com.javashitang.registry;

import com.javashitang.exception.RpcException;
import com.javashitang.loadbalance.LoadBalance;
import com.javashitang.util.SpiUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

import static com.javashitang.registry.CuratorZkUtil.ROOT_PATH;

/**
 * @author lilimin
 * @since 2020-09-24
 */
@Slf4j
public class ZookeeperRegistryService implements RegistryService {

    private final LoadBalance loadBalance = SpiUtil.load(LoadBalance.class);

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        CuratorFramework zkClient = CuratorZkUtil.getZkClient();
        String rootPath = new StringBuilder(ROOT_PATH).append("/").append(serviceName).toString();
        CuratorZkUtil.create(zkClient, rootPath, true);
        CuratorZkUtil.create(zkClient, rootPath + inetSocketAddress.toString(), false);
    }

    @Override
    public InetSocketAddress lookup(String serviceName) {
        log.info("serviceName: {}, loadBalance: {}", serviceName, loadBalance);
        CuratorFramework zkClient = CuratorZkUtil.getZkClient();
        String path = new StringBuilder(ROOT_PATH).append("/").append(serviceName).toString();
        List<String> serviceUrls = CuratorZkUtil.getChildrenNodes(zkClient, path);
        if (CollectionUtils.isEmpty(serviceUrls)) {
            throw new RpcException(RpcException.NO_PROVIDER_EXCEPTION, "no provider");
        }
        String targetServiceUrl = loadBalance.selectService(serviceUrls);
        String[] array = targetServiceUrl.split(":");
        return new InetSocketAddress(array[0], Integer.valueOf(array[1]));
    }
}
