/**
 * 
 */
package com.demolixy.rpc.exportInterface;

import java.io.Serializable;

/**
 * @author lixy
 *
 */
public interface HelloService extends Serializable {

    /**
     * 
     * @param name
     * @return
     */
    String sayName(String name);
    
}
