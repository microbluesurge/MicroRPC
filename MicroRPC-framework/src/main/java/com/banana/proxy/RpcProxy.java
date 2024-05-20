package com.banana.proxy;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Slf4j
public class RpcProxy implements InvocationHandler {

    public RpcProxy() {

    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @SneakyThrows
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("this is the proxy logic...");
        return null;
    }

}
