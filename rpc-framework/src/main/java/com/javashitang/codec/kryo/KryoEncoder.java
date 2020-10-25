package com.javashitang.codec.kryo;

import com.javashitang.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@AllArgsConstructor
public class KryoEncoder extends MessageToByteEncoder {

    private Serializer serializer;
    private Class<?> clazz;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // clazz 是 msg 的父类
        if (clazz.isInstance(msg)) {
            byte[] body = serializer.serialize(msg);
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
        }
    }
}
