/**
 * 
 */
package com.demolixy.complexrpc;

import com.demolixy.complexrpc.provider.RpcProvider;

/**
 * @author lixiangyang
 *
 */
public class ExportHanlder {

    /**
     * 发布服务
     * @param tClz
     * @param t
     */
    public static <T> void export(Class<T> tClz, T t) {
        RpcProvider provider = new RpcProvider();
        provider.serviceInterface(tClz).impl(t).publish();
    }
    
}
