/**
 * 
 */
package com.demolixy.complexrpc;

import java.util.HashMap;
import java.util.Map;

import com.demolixy.complexrpc.consumer.RpcConsumer;


/**
 * @author lixiangyang
 *
 */
public class ReferHanlder {

    private final static Map<String, RpcConsumer> cache = new HashMap<String, RpcConsumer>();
    
    public static <T> T refer(Class<T> refreClass) {
        if(cache.containsKey(refreClass.getName())) {
            return (T) cache.get(refreClass.getName());
        }  
        RpcConsumer noHitCacheCon = new RpcConsumer();
        Object proxyObj = noHitCacheCon.interfaceClass(refreClass).instance();
        cache.put(refreClass.getName(), noHitCacheCon);
        return (T) proxyObj;
    }
    
}
