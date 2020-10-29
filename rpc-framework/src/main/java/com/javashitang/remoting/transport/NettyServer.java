package com.javashitang.remoting.transport;

import com.javashitang.codec.RpcMsgDecoder;
import com.javashitang.codec.RpcMsgEncoder;
import com.javashitang.remoting.exchange.RpcRequest;
import com.javashitang.remoting.exchange.RpcResponse;
import com.javashitang.serialization.KryoSerializer;
import com.javashitang.serialization.Serializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lilimin
 * @since 2020-09-15
 */
public class NettyServer {

    public static final int port = 8080;

    public void start() {

        Serializer serializer = new KryoSerializer();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventLoopGroup eventLoopGroup = new DefaultEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RpcMsgDecoder(serializer, RpcRequest.class));
                            pipeline.addLast(new RpcMsgEncoder(serializer, RpcResponse.class));
                            pipeline.addLast(eventLoopGroup, new NettyServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
