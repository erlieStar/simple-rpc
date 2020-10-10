package com.javashitang.remoting.transport;

import com.javashitang.codec.kryo.KryoDecoder;
import com.javashitang.codec.kryo.KryoEncoder;
import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.serialization.KryoSerializer;
import com.javashitang.serialization.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lilimin
 * @since 2020-09-15
 */
public class NettyClient {

    private NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap;

    public void open() {
        Serializer serializer = new KryoSerializer();
        bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new KryoDecoder(serializer, RpcRequest.class));
                pipeline.addLast(new KryoEncoder(serializer, RpcResponse.class));
                pipeline.addLast(new NettyClientHandler());
            }
        });
    }

    public void connect(InetSocketAddress inetSocketAddress) {
        ChannelFuture future = bootstrap.connect(inetSocketAddress);
    }
}
