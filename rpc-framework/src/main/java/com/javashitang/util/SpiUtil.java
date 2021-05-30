package com.javashitang.util;

import com.javashitang.exception.ServiceLoadException;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author lilimin
 * @since 2021-05-30
 */
public class SpiUtil {

    public synchronized static <S> S load(Class<S> service) {
        return StreamSupport.stream(ServiceLoader.load(service).spliterator(), false)
                .findFirst().orElseThrow(ServiceLoadException::new);
    }
}
