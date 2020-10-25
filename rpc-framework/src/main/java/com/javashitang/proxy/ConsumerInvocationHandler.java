package com.javashitang.proxy;

import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.remoting.transport.NettyTransport;
import com.javashitang.remoting.transport.Transporter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class ConsumerInvocationHandler implements InvocationHandler {

    private Transporter transporter = new NettyTransport();

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(RpcRequest.INVOKE_ID.getAndIncrement())
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .parameters(method.getParameters())
                .build();
        CompletableFuture<RpcResponse> future = (CompletableFuture<RpcResponse>) transporter.sendRequest(rpcRequest);
        RpcResponse response = future.get();
        return response.getResult();
    }
}
