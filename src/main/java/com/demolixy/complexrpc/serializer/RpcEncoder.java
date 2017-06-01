package com.demolixy.complexrpc.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.demolixy.complexrpc.model.RpcRequest;
import com.demolixy.complexrpc.model.RpcResponse;
import com.demolixy.complexrpc.util.Tool;

/**
 * 
 * @author lixiangyang
 *
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private static Object responseCacheName = null;

    private static byte[] responseCacheValue = null;

    private static Object requestCacheName = null;

    private static byte[] requestCacheValue = null;

    private Class<?> genericClass;

    private KryoSerialization kryo;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
        kryo = new KryoSerialization();
        kryo.register(genericClass);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (genericClass.equals(RpcResponse.class)) {//如果是对RpcResponse进行编码    首先把request id指定为""   然后到缓存中找相等的对象的缓存值
            RpcResponse response = (RpcResponse) msg;
            String requestId = response.getRequestId();
            response.setRequestId("");
            byte[] requestIdByte = requestId.getBytes();

            byte[] body = null;
            if (responseCacheName != null && responseCacheName.equals(response)) {
                body = responseCacheValue;
            } else {
                body = Tool.serialize(msg);
                //缓存这个值
                responseCacheName = response;
                responseCacheValue = body;
            }

            int totalLen = requestIdByte.length + 4 + body.length;//总长度为 一个表示 requestid的int 一个 requestid的byte长度 和真实数据的byte长度

            out.writeInt(totalLen);
            out.writeInt(requestIdByte.length);
            out.writeBytes(requestIdByte);
            out.writeBytes(body);
        } else if (genericClass.equals(RpcRequest.class)) {
            RpcRequest request = (RpcRequest) msg;
            String requestId = request.getRequestId();
            request.setRequestId("");
            byte[] requestIdByte = requestId.getBytes();

            byte[] body = null;
            if (requestCacheName != null && requestCacheName.equals(request)) {
                body = requestCacheValue;
            } else {
                body = Tool.serialize(msg);
                //缓存这个值
                requestCacheName = request;
                requestCacheValue = body;
            }

            int totalLen = requestIdByte.length + 4 + body.length;//总长度为 一个表示 requestid的int 一个 requestid的byte长度 和真实数据的byte长度

            out.writeInt(totalLen);
            out.writeInt(requestIdByte.length);
            out.writeBytes(requestIdByte);
            out.writeBytes(body);
        } else {
            byte[] body = Tool.serialize(msg);
            out.writeInt(body.length);
            out.writeBytes(body);
        }
    }

}
