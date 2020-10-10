package com.javashitang.remoting.exchange;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@Data
public class RpcResponse implements Serializable {

    private long requestId;
    private Object result;

    public static RpcResponse success(Long requestId, Object result) {
        RpcResponse response = new RpcResponse();
        response.setRequestId(requestId);
        response.setResult(result);
        return response;
    }

    public static RpcResponse fail(Long requestId, Object result) {
        RpcResponse response = new RpcResponse();
        response.setRequestId(requestId);
        response.setResult(result);
        return response;
    }
}
