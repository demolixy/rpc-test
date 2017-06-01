/**
 * 
 */
package com.demolixy.complexrpc.provider;

/**
 * @author lixiangyang
 *
 */
public interface IRpcProvider {

    
    public IRpcProvider serviceInterface(Class<?> serviceInterface);
    
    public IRpcProvider impl(Object serviceInstance);
    
    public void publish();
    
}
