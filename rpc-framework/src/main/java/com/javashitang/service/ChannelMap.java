package com.javashitang.service;

import com.google.common.collect.Maps;
import com.javashitang.exception.RpcException;
import com.javashitang.remoting.transport.NettyClient;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author lilimin
 * @since 2020-09-22
 */
@Slf4j
public class ChannelMap {

    private static final NettyClient nettyClient = new NettyClient();

    private static final Map<String, Channel> channelMap = Maps.newHashMap();

    public static Channel getChannel(InetSocketAddress address) {
        log.info("address: {}", address);
        String key = address.toString();
        if (channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }
        Channel channel = nettyClient.connect(address);
        if (channel != null && channel.isActive()) {
            channelMap.put(key, channel);
        } else {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, "channel is not active");
        }
        return channel;
    }
}
