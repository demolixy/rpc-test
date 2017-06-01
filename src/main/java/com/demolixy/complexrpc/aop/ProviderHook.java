package com.demolixy.complexrpc.aop;

import com.demolixy.complexrpc.model.RpcRequest;


/**
 * 
 * @author lixiangyang
 *
 */
public interface ProviderHook {
    
    public void before(RpcRequest request);
    
    public void after(RpcRequest request);

}