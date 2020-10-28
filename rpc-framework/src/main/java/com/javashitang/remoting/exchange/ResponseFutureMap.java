package com.javashitang.remoting.exchange;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author lilimin
 * @since 2020-09-24
 */
public class ResponseFutureMap {

    private static final Map<Long, CompletableFuture<RpcResponse>> FUTURES = Maps.newHashMap();

    public static void put(Long requestId, CompletableFuture<RpcResponse> future) {
        FUTURES.put(requestId, future);
    }

    public static void received(RpcResponse response) {
        CompletableFuture future = FUTURES.remove(response.getRequestId());
        if (future != null) {
            future.complete(response);
        }
    }
}
