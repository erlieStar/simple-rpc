package com.javashitang.remoting.transport;

import com.javashitang.remoting.exchange.ResponseFutureMap;
import com.javashitang.remoting.exchange.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lilimin
 * @since 2020-09-15
 */
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        ResponseFutureMap.received(msg);
    }
}
