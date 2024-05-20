package com.banana.spring;


import com.banana.annotations.RpcCall;
import com.banana.proxy.RpcProxy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {



//    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcCall rpcReference = declaredField.getAnnotation(RpcCall.class);
            if (rpcReference != null) {
//                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                        .group(rpcReference.group())
//                        .version(rpcReference.version()).build();
                RpcProxy rpcClientProxy = new RpcProxy();
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

}
