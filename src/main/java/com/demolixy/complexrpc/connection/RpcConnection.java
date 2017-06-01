/**
 * 
 */
package com.demolixy.complexrpc.connection;

import java.util.List;

import com.demolixy.complexrpc.model.RpcRequest;

/**
 * @author lixiangyang
 *
 */
public interface RpcConnection {
    void init();
    void connect();
    void connect(String host,int port);
    Object Send(RpcRequest request,boolean async);
    void close();
    boolean isConnected();
    boolean isClosed();
    public boolean containsFuture(String key);
    public InvokeFuture<Object> removeFuture(String key);
    public void setResult(Object ret);
    public void setTimeOut(long timeout);
//    public void setAsyncMethod(Map<String,ResponseCallbackListener> map);
    public List<InvokeFuture<Object>> getFutures(String method);
}
