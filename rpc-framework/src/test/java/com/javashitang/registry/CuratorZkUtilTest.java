package com.javashitang.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lilimin
 * @since 2021-06-04
 */
@Slf4j
public class CuratorZkUtilTest {


    private CuratorFramework client;

    @Test
    @Before
    public void connect() {
        String connectString = "myhost:2181";
        // 重试3次，每次间隔1000ms
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
    }

    @Test
    public void getChildrenNodes() {
        String path = "/simple-rpc/aaa";
        System.out.println(CuratorZkUtil.getChildrenNodes(client, path));
    }

    @Test
    public void getChildrenNodesTestCase2() {
        String path = "/simple-rpc/com.javashitang.StudentService";
        List<String> childrenNodes = CuratorZkUtil.getChildrenNodes(client, path);
        System.out.println(childrenNodes);
    }

    @Test
    public void testEvent() throws Exception {

        String bossPath = "/pathCache";
        String workerPath = "/pathCache/id-";

        if (client.checkExists().forPath(bossPath) == null) {
            client.create().forPath(bossPath);
        }

        PathChildrenCache cache = new PathChildrenCache(client, bossPath, true);
        PathChildrenCacheListener listener = ((CuratorFramework client, PathChildrenCacheEvent event) -> {
            String changePath = event.getData().getPath();
            changePath = changePath.substring(changePath.lastIndexOf("/") + 1);
            log.info("changePath: {}", changePath);
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_UPDATED:
                    log.info("child add or update");
                    break;
                case CHILD_REMOVED:
                    log.info("child removed");
                    break;
                default:
                    break;
            }
            log.info("事件类型为: {}, 路径为: {}", event.getType(), changePath);
        });

        cache.getListenable().addListener(listener);
        cache.start();

        log.info("开始监听");
        System.in.read();

    }
}