package com.javashitang.service;

import com.google.common.collect.Maps;
import com.javashitang.remoting.exchange.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author lilimin
 * @since 2020-10-25
 */
public class UnProcessedMap {

    private static final Map<Long, CompletableFuture<RpcResponse>> unProcessedMsg = Maps.newConcurrentMap();

    public static void put(Long requestId, CompletableFuture<RpcResponse> future) {
        unProcessedMsg.put(requestId, future);
    }

    public static void complete(RpcResponse response) {
        CompletableFuture<RpcResponse> future = unProcessedMsg.remove(response.getRequestId());
        if (future != null) {
            future.complete(response);
        }

    }
}
