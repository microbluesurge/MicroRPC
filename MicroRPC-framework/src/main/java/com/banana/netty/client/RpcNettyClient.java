package com.banana.netty.client;

import com.alibaba.fastjson2.JSON;
import com.banana.netty.dto.RpcCallRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

@Slf4j
public class RpcNettyClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;


    Map<Integer, Integer> mm = new HashMap<>();
    public RpcNettyClient() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //  The timeout period of the connection.
                //  If this time is exceeded or the connection cannot be established, the connection fails.
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        // If no data is sent to the server within 15 seconds, a heartbeat request is sent
                        p.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        p.addLast(new StringEncoder());
                        p.addLast(new StringDecoder());
                        p.addLast(new RpcNettyClientHandler());
                    }
                });
    }
    @SneakyThrows
    public Object sendRpcRequest(String str) {
        ChannelFuture future = bootstrap.connect("192.168.136.1", 11451).sync();
//        future.s
        future.channel().writeAndFlush(str);
        return null;
    }


    @SneakyThrows
    public Object sendRpcRequest(RpcCallRequest request) {

        ChannelFuture future = bootstrap.connect(InetAddress.getLocalHost().getHostAddress(), 11451).sync();
//        future.s
        ChannelFuture resultFuture = future.channel().writeAndFlush(JSON.toJSONString(request));
        return null;
    }




}
