package com.javashitang.serialization;

/**
 * @author lilimin
 * @since 2020-09-21
 */
public interface Serializer {

    /**
     * 序列化
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
