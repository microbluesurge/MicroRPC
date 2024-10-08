package com.orange;

import com.alibaba.fastjson2.JSON;
import com.banana.annotations.RpcService;
import com.test.api.Person;
import com.test.api.PersonService;
@RpcService
public class PersonServiceImpl implements PersonService {
    @Override
    public String createPerson(String name, int weight, int height)
    {
        Person person=new Person(name,weight,height);
        return JSON.toJSONString(person);
    }
}
