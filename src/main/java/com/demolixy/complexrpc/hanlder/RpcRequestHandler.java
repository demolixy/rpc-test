package com.demolixy.complexrpc.hanlder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import com.demolixy.complexrpc.model.RpcRequest;
import com.demolixy.complexrpc.model.RpcResponse;
import com.demolixy.complexrpc.serializer.KryoSerialization;
import com.demolixy.complexrpc.util.ByteObjConverter;
import com.demolixy.complexrpc.util.Tool;


/**
 * @author lixiangyang
 *
 */
public class RpcRequestHandler extends ChannelInboundHandlerAdapter {
    
    KryoSerialization kryo = new KryoSerialization();

    private Map<String, Object> hanlderMap = new HashMap<String, Object>();
    
    
    /**
     * @param hanlderMap2
     */
    public RpcRequestHandler(Map<String, Object> hanlderMap) {
        this.hanlderMap = hanlderMap;
    }

    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
//        String host = ctx.channel().remoteAddress().toString();
        
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try{
            Object result = hanlder(request);
            response.setAppResponse(ByteObjConverter.ObjectToByte(result));
        } catch (Throwable t) {
            response.setExption(Tool.serialize(t));
            response.setClazz(t.getClass());
        }
        ctx.writeAndFlush(response);
    }
    
    /**
     * 处理请求
     * @param request
     * @return
     * @throws InvocationTargetException 
     */
    private Object hanlder(RpcRequest request) throws InvocationTargetException {
        String className = request.getClassName();
        Object impObj = hanlderMap.get(className);
        
        Class<?> impClass = impObj.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        
        
        FastClass serviceFastClass = FastClass.create(impClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(impObj, parameters);
    }
    
}

