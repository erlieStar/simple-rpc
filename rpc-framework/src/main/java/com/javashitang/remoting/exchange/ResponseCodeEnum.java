package com.javashitang.remoting.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lilimin
 * @since 2021-06-01
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    SUCCESS(200, "success"),
    FAIL(500, "fail");

    private final int code;
    private final String message;
}
