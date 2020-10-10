package com.javashitang.remoting.transport;

import com.javashitang.remoting.exchange.ReponseFutureMap;
import com.javashitang.remoting.exchange.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lilimin
 * @since 2020-09-15
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        ReponseFutureMap.received(msg);
    }
}
