package com.javashitang.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.javashitang.exception.RpcException;
import com.javashitang.util.PropertiesUtil;
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

    public static final String ROOT_PATH = "/simple-rpc";
    private static CuratorFramework zkClient;
    // 类似 Map<String, List<String>>
    private static final Multimap<String, String> serviceMap = ArrayListMultimap.create();
    private static String defaultZkAddress = PropertiesUtil.getProperty("registry.address");

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
                    zkClient.start();
                }
            }
        }
        return zkClient;
    }

    // 创建节点
    public static void create(CuratorFramework zkClient, String path, boolean isPersistent) {
        try {
            if (zkClient.checkExists().forPath(path) != null) {
                log.info("path {} is already exists", path);
            } else {
                if (isPersistent) {
                    zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                } else {
                    zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
                }
            }
        } catch (Exception e) {
            log.error("createPersistentMode error", e);
            throw new RpcException(e.getMessage(), e);
        }
    }

    // 获取子节点
    public static List<String> getChildrenNodes(CuratorFramework zkClient, String path) {
        log.info("getChildrenNodes param path: {}", path);
        if (serviceMap.containsKey(path)) {
            log.info("hit cache");
            return (List<String>) serviceMap.get(path);
        }
        List<String> result = null;
        try {
            result = zkClient.getChildren().forPath(path);
            registryWatcher(zkClient, path);
        } catch (Exception e) {
            log.error("getChildrenNodes error", e);
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }

    // 注册监听
    public static void registryWatcher(CuratorFramework zkClient, String path) {
        // 1.只能监听子节点，监听不到当前节点
        // 2.不能递归监听，即监听不到子节点下的子节点
        PathChildrenCache cache = new PathChildrenCache(zkClient, path, true);
        PathChildrenCacheListener listener = ((CuratorFramework client, PathChildrenCacheEvent event) -> {
            String changePath = event.getData().getPath();
            changePath = changePath.substring(changePath.lastIndexOf("/") + 1);
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_UPDATED:
                    log.info("child add or update");
                    serviceMap.put(path, changePath);
                    break;
                case CHILD_REMOVED:
                    log.info("child removed");
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
