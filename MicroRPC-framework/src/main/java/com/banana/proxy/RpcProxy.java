package com.banana.proxy;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.banana.netty.dto.RpcCallRequest;
import com.banana.netty.client.RpcNettyClient;
import com.banana.utils.SingletenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Slf4j
public class RpcProxy implements InvocationHandler {


    private static RpcNettyClient client;

//    public Object tar;
    public RpcProxy() {
        client = SingletenUtil.getInstance(RpcNettyClient.class);
//        client  = new RpcNettyClient();
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @SneakyThrows
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
//        System.out.println("this is the proxy logic...");
//        System.out.println("method to call is " + method.getName());
        System.out.println(method.getDeclaringClass().getName());
//        for(Object arg : args) {
//            System.out.println("args is " + arg);
//        }
        RpcCallRequest request = RpcCallRequest.builder()
                .methodName(method.getName())
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .params(args)
                .build();
        //logs.....
//        tar.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(tar, args);
        client.sendRpcRequest(request);
        return null;
    }

}
