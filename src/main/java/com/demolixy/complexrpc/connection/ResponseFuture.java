package com.demolixy.complexrpc.connection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.demolixy.complexrpc.model.RpcResponse;

/**
 * 
 * @author lixiangyang
 *
 */
public class ResponseFuture {
    
    
    public static ThreadLocal<Future<Object>> futureThreadLocal = new ThreadLocal<Future<Object>>();

    public static Object getResponse(long timeout) throws InterruptedException {
        if (null == futureThreadLocal.get()) {
            throw new RuntimeException("Thread [" + Thread.currentThread() + "] have not set the response future!");
        }

        try {
            RpcResponse response = (RpcResponse) (futureThreadLocal.get().get(timeout, TimeUnit.MILLISECONDS));
            if (response.isError()) {
                throw new RuntimeException(response.getErrorMsg());
            }
            return response.getAppResponse();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException("Time out", e);
        }
    }

    public static void setFuture(Future<Object> future){
        futureThreadLocal.set(future);
    }
}