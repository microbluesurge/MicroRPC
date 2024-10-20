package com.banana.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这个注释用于所有的RPC调用上
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcCall {



}


