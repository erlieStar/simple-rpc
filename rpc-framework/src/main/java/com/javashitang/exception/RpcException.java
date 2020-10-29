package com.javashitang.exception;

/**
 * @author lilimin
 * @since 2020-09-23
 */
public class RpcException extends RuntimeException {

    public static final int NETWORK_EXCEPTION = 0;
    public static final int BIZ_EXCEPTION = 1;
    public static final int NO_PROVIDER_EXCEPTION = 2;
    public static final int SERIALIZATION_EXCEPTION = 3;

    private int code;

    public RpcException(String message) {
        super(message);
    }

    public RpcException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
