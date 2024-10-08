package com.banana;

public interface ServiceProvider {


    void addService(Object rpcService);


    Object getService(String name);

}
