/**
 * 
 */
package com.demolixy.rpc;

import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lixy
 *
 */
public class RpcFramework {

    /**
     * 
     * @param port
     * @param exportObj
     * @throws Exception
     */
    public static void export(int port, final Object exportObj) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true) {
            final Socket sockt = serverSocket.accept();
            InvokeHanlder h = new InvokeHanlder(exportObj, sockt);
            new Thread(h).start();
        }
    }
    
    /**
     * 
     * @param ip
     * @param port
     * @param interfaceClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(String ip, int port, Class<T> interfaceClass) {
        ReferHanlder r = new ReferHanlder(ip, port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, r);
    }
    
    
}
