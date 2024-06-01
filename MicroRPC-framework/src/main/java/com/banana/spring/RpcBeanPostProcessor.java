package com.banana.spring;


import com.banana.P2pServiceProvider;
import com.banana.ServiceProvider;
import com.banana.annotations.RpcCall;
import com.banana.annotations.RpcService;
import com.banana.proxy.RpcProxy;
import com.banana.utils.SingletenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {


    private final ServiceProvider provider;

    public RpcBeanPostProcessor() {
        provider = SingletenUtil.getInstance(P2pServiceProvider.class);
    }


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


    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            System.out.println(bean.getClass().getName() + " hhh " + RpcService.class.getCanonicalName());
            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            // get RpcService annotation
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            provider.addService(bean);
            // build RpcServiceProperties
//            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                    .group(rpcService.group())
//                    .version(rpcService.version())
//                    .service(bean).build();
//            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }

}
