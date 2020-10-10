package com.javashitang.exception;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class RpcException extends RuntimeException {

    private int code;

    public RpcException(String message) {
        super(message);
    }
}
