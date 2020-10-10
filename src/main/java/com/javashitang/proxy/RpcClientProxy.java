package com.javashitang.proxy;

import com.javashitang.config.ReferenceConfig;
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
public class RpcClientProxy implements InvocationHandler {

    private ReferenceConfig referenceConfig;
    private Transporter transporter;

    public RpcClientProxy(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
        transporter = new NettyTransport();
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(RpcRequest.INVOKE_ID.getAndIncrement())
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .version(referenceConfig.getVersion())
                .paramTypes(method.getParameterTypes())
                .parameters(method.getParameters())
                .build();
        RpcResponse response = null;
        CompletableFuture<RpcResponse> future = (CompletableFuture<RpcResponse>) transporter.sendRequest(rpcRequest);
        response = future.get();
        return response.getResult();
    }
}
