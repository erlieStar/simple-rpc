package com.javashitang.registry;

import com.javashitang.exception.RpcException;
import com.javashitang.loadbalance.LoadBalance;
import com.javashitang.loadbalance.RandomLoadBalance;
import com.javashitang.util.SpiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

import static com.javashitang.registry.CuratorZkUtil.ROOT_PATH;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class ZookeeperRegistryService implements RegistryService {

    private final LoadBalance loadBalance = new RandomLoadBalance();

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        CuratorFramework zkClient = CuratorZkUtil.getZkClient();
        String rootPath = new StringBuilder(ROOT_PATH).append("/").append(serviceName).toString();
        CuratorZkUtil.create(zkClient, rootPath, true);
        CuratorZkUtil.create(zkClient, rootPath + inetSocketAddress.toString(), false);
    }

    @Override
    public InetSocketAddress lookup(String serviceName) {
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

    public static void main(String[] args) {
        RegistryService service = SpiUtil.load(RegistryService.class);
        System.out.println(service);
    }
}
