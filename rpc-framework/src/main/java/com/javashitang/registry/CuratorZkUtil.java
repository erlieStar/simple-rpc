package com.javashitang.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.javashitang.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author lilimin
 * @since 2020-09-24
 */
@Slf4j
public class CuratorZkUtil {

    private static CuratorFramework zkClient;
    private static final String ROOT_PATH = "/simple-dubbo";
    // 类似 Map<String, List<String>>
    private static final Multimap<String, String> serviceMap = ArrayListMultimap.create();
    private static String defaultZkAddress = "myhost:2181";

    private CuratorZkUtil() {}

    public static void setZkAddress(String address) {
        defaultZkAddress = address;
    }

    public static CuratorFramework getZkClient() {
        // 重试3次，每次间隔1000ms
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        if (zkClient == null) {
            synchronized (CuratorZkUtil.class) {
                if (zkClient == null) {
                    zkClient = CuratorFrameworkFactory.newClient(defaultZkAddress, retryPolicy);
                }
            }
        }
        zkClient.start();
        return zkClient;
    }

    // 创建持久节点
    public static void createPersistentMode(CuratorFramework zkClient, String path) {
        try {
            path = new StringBuilder(ROOT_PATH).append("/").append(path).toString();
            if (zkClient.checkExists().forPath(path) != null) {
                log.info("path {} is already exists", path);
            } else {
                zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
        } catch (Exception e) {
            log.error("createPersistentMode error", e);
        }
    }

    // 获取子节点
    public static List<String> getChildrenNodes(CuratorFramework zkClient, String path) {
        path = new StringBuilder(ROOT_PATH).append("/").append(path).toString();
        if (serviceMap.containsKey(path)) {
            return (List<String>) serviceMap.get(path);
        }
        List<String> result = null;
        try {
            result = zkClient.getChildren().forPath(path);
            serviceMap.putAll(path, result);
            registryWatcher(zkClient, path);
        } catch (Exception e) {
            throw new RpcException(e.getMessage(), e.getCause());
        }
        return result;
    }

    // 注册监听
    public static void registryWatcher(CuratorFramework zkClient, String path) {
        // 1.只能监听子节点，监听不到当前节点
        // 2.不能递归监听，即监听不到子节点下的子节点
        PathChildrenCache cache = new PathChildrenCache(zkClient, path, true);
        PathChildrenCacheListener listener = ((CuratorFramework client, PathChildrenCacheEvent event) -> {
            ChildData data = event.getData();
            String changePath = ROOT_PATH + data.getPath();
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_UPDATED:
                    serviceMap.put(path, changePath);
                    break;
                case CHILD_REMOVED:
                    serviceMap.remove(path, changePath);
                    break;
                default:
                    break;
            }
        });
        cache.getListenable().addListener(listener);
        try {
            cache.start();
        } catch (Exception e) {
            log.error("registryWatcher error", e);
        }
    }

}
