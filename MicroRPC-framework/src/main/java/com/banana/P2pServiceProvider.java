package com.banana;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class P2pServiceProvider implements ServiceProvider{

    private final Map<String, Object> serviceMap;


    public P2pServiceProvider() {
        this.serviceMap = new ConcurrentHashMap<>();
    }


    @Override
    public void addService(Object rpcService) {
        String name = rpcService.getClass().getInterfaces()[0].getCanonicalName();
        serviceMap.put(name, rpcService);
    }

    @Override
    public Object getService(String name) {
        if(!serviceMap.containsKey(name)) {
            throw new RuntimeException("no such bean : " + name);
        }
        return serviceMap.get(name);
    }

}
