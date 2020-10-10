package com.javashitang.serialization;

/**
 * @author lilimin
 * @since 2020-09-21
 */
public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
