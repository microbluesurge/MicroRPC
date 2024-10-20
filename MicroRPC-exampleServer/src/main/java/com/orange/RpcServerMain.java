package com.orange;

import com.banana.netty.server.RpcNettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(value = {"com.orange", "com.banana"})
public class RpcServerMain
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RpcServerMain.class);
        RpcNettyServer rpcNettyServer = (RpcNettyServer) applicationContext.getBean("rpcNettyServer");
        rpcNettyServer.start();
    }
}
