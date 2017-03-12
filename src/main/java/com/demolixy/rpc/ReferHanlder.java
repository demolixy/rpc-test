/**
 * 
 */
package com.demolixy.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author lixy
 *
 */
public class ReferHanlder implements InvocationHandler {


    private String ip;
    
    private int port;
    
    private Socket socket;
    
    
    /**
     * 
     * @param ip
     * @param port
     */
    public ReferHanlder(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        socket = new Socket(ip, port);
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeUTF(method.getName());
            oos.writeObject(method.getParameterTypes());
            oos.writeObject(args);
            ois = new ObjectInputStream(socket.getInputStream());
            Object result = ois.readObject();
            return result;
        } finally {
            if(oos != null) {
                oos.close();
            }
            if(ois != null) {
                ois.close();
            }
            if(socket != null) {
                socket.close();
            }
        }
    }
    
}
