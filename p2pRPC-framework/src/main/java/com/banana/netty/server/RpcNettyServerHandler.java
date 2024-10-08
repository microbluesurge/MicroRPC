package com.banana.netty.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.banana.P2pServiceProvider;
import com.banana.ServiceProvider;
import com.banana.netty.dto.RpcCallRequest;
import com.banana.netty.dto.RpcCallResponse;
import com.banana.utils.SingletenUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class RpcNettyServerHandler extends ChannelInboundHandlerAdapter {

    private final  ServiceProvider provider;
    public RpcNettyServerHandler() {
        provider = SingletenUtil.getInstance(P2pServiceProvider.class);

    }
    @Override
    @SneakyThrows
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof String) {
                RpcCallRequest request = JSON.parseObject((String) msg, RpcCallRequest.class,
                        JSONReader.Feature.SupportClassForName);
                Object service  = provider.getService(request.getInterfaceName());
                Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());

                Object result = method.invoke(service, request.getParams());
                String retStr = JSON.toJSONString(RpcCallResponse.newSuccessResponse(result));
                System.out.println(retStr);
                ctx.writeAndFlush(retStr).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }
}
