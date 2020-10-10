package com.javashitang.codec.kryo;

import com.javashitang.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author lilimin
 * @since 2020-09-23
 */
@AllArgsConstructor
public class KryoDecoder extends ByteToMessageDecoder {

    private Serializer serializer;
    private Class<?> clazz;
    private static final int BODY_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= BODY_LENGTH) {
            in.markReaderIndex();
            int dataLength = in.readInt();
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            byte[] body = new byte[dataLength];
            in.readBytes(body);
            Object object = serializer.deserialize(body, clazz);
            out.add(object);
        }
    }
}
