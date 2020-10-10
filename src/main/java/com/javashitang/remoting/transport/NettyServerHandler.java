package com.javashitang.remoting.transport;

import com.javashitang.exception.RpcException;
import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.service.ServiceMap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author lilimin
 * @since 2020-09-15
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse response = invokeMethod(msg);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    public RpcResponse invokeMethod(RpcRequest request) {
        Object result;
        try {
            StringBuffer sb = new StringBuffer();
            String serviceKey = sb.append(request.getInterfaceName()).append(":").append(request.getMethodName()).append(":")
                    .append(request.getVersion()).toString();
            Object service = ServiceMap.getService(serviceKey);
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(service, request.getParameters());
        } catch (Exception e) {
            throw new RpcException("invoke failed");
        }
        return RpcResponse.success(request.getRequestId(), result);
    }
}
