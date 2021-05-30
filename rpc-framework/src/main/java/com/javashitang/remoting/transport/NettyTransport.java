package com.javashitang.remoting.transport;

import com.javashitang.registry.RegistryService;
import com.javashitang.remoting.exchange.ResponseFutureMap;
import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.service.ChannelMap;
import com.javashitang.util.SpiUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@Slf4j
public class NettyTransport implements Transporter {

    private RegistryService registryService = SpiUtil.load(RegistryService.class);

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest request) {
        String serviceName = request.getInterfaceName();
        InetSocketAddress address = registryService.lookup(serviceName);
        Channel channel = ChannelMap.getChannel(address);
        if (channel == null || !channel.isActive()) {
            throw new IllegalStateException();
        }
        CompletableFuture<RpcResponse> requestFuture = new CompletableFuture();
        ResponseFutureMap.put(request.getRequestId(), requestFuture);
        channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("send msg: {} success", request);
            } else {
                log.error("send msg: {} failed", request);
                future.channel().close();
                requestFuture.completeExceptionally(future.cause());
            }
        });
        return requestFuture;
    }
}
