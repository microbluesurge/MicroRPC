package com.orange;

import com.banana.annotations.RpcService;
import com.test.api.TestService;
import org.springframework.stereotype.Service;


@RpcService
public class TestServiceImpl implements TestService {
    @Override
    public String hello(String hello) {
        System.out.println("this is the server implement(version 1) of the interface TestService");
        System.out.println("the param input is " + hello);
        return "goodbye! this is implement version 1";
    }
}
