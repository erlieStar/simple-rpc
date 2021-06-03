package com.javashitang.autoconfigure;

import com.javashitang.remoting.transport.NettyServer;
import org.springframework.boot.SpringApplicationRunListener;

/**
 * @author lilimin
 * @since 2021-06-03
 */
public class RpcApplicationRunListener implements SpringApplicationRunListener {

    @Override
    public void starting() {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
