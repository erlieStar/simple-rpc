package com.javashitang.invoke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lilimin
 * @since 2020-10-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] parameters;
}
