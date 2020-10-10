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

    private NettyClient nettyClient;

    private Map<String, Channel> channelMap = Maps.newHashMap();

    public Object getChannel(InetSocketAddress address) {
        return channelMap.get(address.toString());
    }

    public void removeChannel(InetSocketAddress address) {
        channelMap.remove(address.toString());
    }
}
