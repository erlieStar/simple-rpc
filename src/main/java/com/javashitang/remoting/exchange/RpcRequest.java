package com.javashitang.remoting.exchange;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    public static final AtomicLong INVOKE_ID = new AtomicLong(0);
    private long requestId;
    private String interfaceName;
    private String methodName;
    private String version;
    private Class<?>[] paramTypes;
    private Object[] parameters;
}
