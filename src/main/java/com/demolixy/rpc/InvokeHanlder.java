/**
 * 
 */
package com.demolixy.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author lixy
 *
 */
public class InvokeHanlder implements Runnable {

    private Object exportObj;
    
    private Socket socket;
    
    public InvokeHanlder(Object exportObject, Socket socket) {
        this.exportObj = exportObject;
        this.socket = socket;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            String methodName = ois.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();
            Method method = exportObj.getClass().getDeclaredMethod(methodName, parameterTypes);
            Object result = method.invoke(exportObj, args);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
