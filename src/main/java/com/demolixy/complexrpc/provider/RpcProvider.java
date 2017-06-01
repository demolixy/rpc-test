/**
 * 
 */
package com.demolixy.complexrpc.provider;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashMap;
import java.util.Map;

import com.demolixy.complexrpc.hanlder.RpcRequestHandler;
import com.demolixy.complexrpc.model.RpcRequest;
import com.demolixy.complexrpc.model.RpcResponse;
import com.demolixy.complexrpc.serializer.RpcDecoder;
import com.demolixy.complexrpc.serializer.RpcEncoder;

/**
 * @author lixiangyang
 *
 */
public class RpcProvider implements IRpcProvider {

    private Map<String, Object> hanlderMap = new HashMap<String, Object>();
    
    private Class<?> interfaceClzz;
    
    private Object classImple;
    
    
    /* (non-Javadoc)
     * @see com.demolixy.complexrpc.provider.IRpcProvider#serviceInterface(java.lang.Class)
     */
    @Override
    public IRpcProvider serviceInterface(Class<?> serviceInterface) {
        this.interfaceClzz = serviceInterface;
        return this;
    }

    /* (non-Javadoc)
     * @see com.demolixy.complexrpc.provider.IRpcProvider#impl(java.lang.Object)
     */
    @Override
    public IRpcProvider impl(Object serviceInstance) {
        this.classImple = serviceInstance;
        return this;
    }

    /* (non-Javadoc)
     * @see com.demolixy.complexrpc.provider.IRpcProvider#publish()
     */
    @Override
    public void publish() {
        hanlderMap.put(interfaceClzz.getName(), classImple);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // server端采用简洁的连写方式，client端才用分段普通写法。
           serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
           .handler(new LoggingHandler(LogLevel.INFO))
           .childHandler(new ChannelInitializer<SocketChannel>() {
                           @Override
                           public void initChannel(SocketChannel ch)
                                    throws Exception {
                            ch.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            ch.pipeline().addLast(new RpcDecoder(RpcRequest.class));
//                          ch.pipeline().addLast(new FSTNettyEncode());
//                            ch.pipeline().addLast(new FSTNettyDecode());
                            ch.pipeline().addLast(new RpcRequestHandler(hanlderMap));
                          }
                     })
                     .option(ChannelOption.SO_KEEPALIVE , true )
                     .childOption(ChannelOption.TCP_NODELAY, true)
                     .option(ChannelOption.SO_SNDBUF, 1024)
                     .option(ChannelOption.SO_RCVBUF, 2048);
           
           
           ChannelFuture f = serverBootstrap.bind(8888).sync();
           f.channel().closeFuture().sync();
       } catch (InterruptedException e) {
       } finally {
           workerGroup.shutdownGracefully();
           bossGroup.shutdownGracefully();
       }
     
    }

}
