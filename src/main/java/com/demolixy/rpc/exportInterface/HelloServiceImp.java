/**
 * 
 */
package com.demolixy.rpc.exportInterface;

/**
 * @author lixy
 *
 */
public class HelloServiceImp implements HelloService {

    /**
     * 
     */
    private static final long serialVersionUID = -8097102897696521096L;

    /* (non-Javadoc)
     * @see com.demolixy.rpc.exportInterface.HelloService#sayName(java.lang.String)
     */
    public String sayName(String name) {
        return "this name is " + name;
    }

}
