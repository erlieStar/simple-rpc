package com.javashitang.exception;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class RpcException extends RuntimeException {

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(String message) {
        super(message);
    }
}
