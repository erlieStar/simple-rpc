package com.javashitang.remoting.exchange;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class ReponseFutureMap {

    private static final Map<Long, CompletableFuture> FUTURES = new ConcurrentHashMap<>();

    public static void received(RpcResponse response) {
        CompletableFuture future = FUTURES.remove(response.getRequestId());
        if (future != null) {
            future.complete(response.getResult());
        }
    }
}
