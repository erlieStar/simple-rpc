package com.javashitang.remoting.transport;

import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class NettyTransport implements Transporter {

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest request) {
        CompletableFuture<RpcResponse> future = new CompletableFuture();

        return null;
    }
}
