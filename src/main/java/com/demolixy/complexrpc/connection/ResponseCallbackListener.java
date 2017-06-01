/**
 * 
 */
package com.demolixy.complexrpc.connection;

/**
 * @author lixiangyang
 *
 */
public interface ResponseCallbackListener {
    void onResponse(Object response);
    void onTimeout();
    void onException(Exception e);
}

