package com.demolixy.complexrpc.aop;

import com.demolixy.complexrpc.model.RpcRequest;

/**
 * 
 * @author lixiangyang
 *
 */
public interface ConsumerHook {
    
    public void before(RpcRequest request);
    
    public void after(RpcRequest request);
    
}