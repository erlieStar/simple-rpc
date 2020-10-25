package com.javashitang.service;

import com.google.common.collect.Maps;
import com.javashitang.remoting.transport.NettyClient;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public class ChannelMap {

    private static final NettyClient nettyClient = new NettyClient();

    private static final Map<String, Channel> channelMap = Maps.newHashMap();

    public static Channel getChannel(InetSocketAddress address) {
        String key = address.toString();
        if (channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);
            return channel;
        }
        Channel channel = nettyClient.connect(address);
        return channel;
    }

    public static void removeChannel(InetSocketAddress address) {
        channelMap.remove(address.toString());
    }
}
