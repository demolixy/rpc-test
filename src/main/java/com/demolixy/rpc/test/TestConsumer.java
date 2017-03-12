/**
 * 
 */
package com.demolixy.rpc.test;

import com.demolixy.rpc.RpcFramework;
import com.demolixy.rpc.exportInterface.HelloService;

/**
 * @author lixy
 *
 */
public class TestConsumer {

    public static void main(String[] args) {
        HelloService hello = RpcFramework.refer("127.0.0.1", 8888, HelloService.class);
        System.out.println(hello.sayName("hello"));
    }
    
}
