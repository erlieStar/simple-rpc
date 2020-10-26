package com.javashitang.remoting.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    private long requestId;
    private String interfaceName;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] parameters;
}
