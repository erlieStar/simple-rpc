package com.javashitang.remoting.transport;

import com.javashitang.exception.RpcException;
import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.service.ServiceMap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author lilimin
 * @since 2020-09-15
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        log.info("channelRead0");
        RpcResponse response = invokeMethod(msg);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    public RpcResponse invokeMethod(RpcRequest request) {
        log.info("invokeMethod param request: {}", request.toString());
        Object result;
        try {
            String serviceKey = request.getInterfaceName();
            Object service = ServiceMap.getService(serviceKey);
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(service, request.getParameters());
        } catch (Exception e) {
            log.error("invokeMethod error", e);
            throw new RpcException(RpcException.BIZ_EXCEPTION, "invoke failed");
        }
        return RpcResponse.success(request.getRequestId(), result);
    }
}
