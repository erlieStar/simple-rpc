package com.javashitang.remoting.exchange;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@Data
@NoArgsConstructor
public class RpcResponse implements Serializable {

    private long requestId;
    private Integer code;
    private String message;
    private Object result;

    public static RpcResponse success(Long requestId, Object result) {
        RpcResponse response = new RpcResponse();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setRequestId(requestId);
        response.setResult(result);
        return response;
    }

    public static RpcResponse fail(Long requestId, String message) {
        RpcResponse response = new RpcResponse();
        response.setCode(ResponseCodeEnum.FAIL.getCode());
        response.setRequestId(requestId);
        response.setMessage(message);
        return response;
    }

    public boolean isSuccess() {
        return this.code.equals(ResponseCodeEnum.SUCCESS.getCode());
    }
}
