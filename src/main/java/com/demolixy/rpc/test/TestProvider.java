/**
 * 
 */
package com.demolixy.rpc.test;

import com.demolixy.rpc.RpcFramework;
import com.demolixy.rpc.exportInterface.HelloService;
import com.demolixy.rpc.exportInterface.HelloServiceImp;

/**
 * @author lixy
 *
 */
public class TestProvider {

    public static void main(String[] args) throws Exception {
        HelloService hello = new HelloServiceImp();
        RpcFramework.export(8888, hello);
    }
    
}
