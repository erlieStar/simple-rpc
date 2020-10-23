package com.javashitang.registry;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class CuratorZkClient {

    private volatile static CuratorZkClient curatorZkClient;

    private static final String ROOT_PATH = "/simple-dubbo";

    private CuratorZkClient() {}

    public static CuratorZkClient getInstance() {
        if (curatorZkClient == null) {
            synchronized (CuratorZkClient.class) {
                if (curatorZkClient != null) {
                    curatorZkClient = new CuratorZkClient();
                }
            }
        }
        return curatorZkClient;
    }

}
