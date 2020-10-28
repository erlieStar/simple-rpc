package com.javashitang.proxy;

import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.remoting.transport.NettyTransport;
import com.javashitang.remoting.transport.Transporter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class ConsumerInvocationHandler implements InvocationHandler {

    public static final AtomicLong INVOKE_ID = new AtomicLong(0);

    private Transporter transporter = new NettyTransport();

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(INVOKE_ID.getAndIncrement())
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        CompletableFuture<RpcResponse> future = (CompletableFuture<RpcResponse>) transporter.sendRequest(rpcRequest);
        RpcResponse response = future.get();
        return response.getResult();
    }
}
