package com.javashitang.remoting.transport;

import com.javashitang.remoting.exchange.RpcRequest;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public interface Transporter {

    Object sendRequest(RpcRequest request);
}
