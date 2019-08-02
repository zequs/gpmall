package com.gpmall.order.biz.factory;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.TransOutboundInvoker;
import com.gpmall.order.biz.TransPipeline;
import com.gpmall.order.biz.context.CreateOrderContext;
import com.gpmall.order.biz.context.TransHandlerContext;
import com.gpmall.order.biz.convert.CreateOrderConvert;
import com.gpmall.order.biz.convert.TransConvert;
import com.gpmall.order.biz.handler.ClearCartItemHandler;
import com.gpmall.order.biz.handler.InitOrderHandler;
import com.gpmall.order.biz.handler.LogisticalHandler;
import com.gpmall.order.biz.handler.ValidateHandler;
import com.gpmall.order.dto.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:52
 *
 * 构建订单处理链
 */
@Slf4j
@Service
public class OrderProcessPipelineFactory extends AbstranctTransPipelineFactory<CreateOrderRequest> {

    @Autowired
    private InitOrderHandler initOrderHandler;
    @Autowired
    private ValidateHandler validateHandler;
    @Autowired
    private LogisticalHandler logisticalHandler;
    @Autowired
    private ClearCartItemHandler clearCartItemHandler;

    @Override
    protected TransHandlerContext createContext() {
        return new CreateOrderContext();
    }

    @Override
    protected void doBuild(TransPipeline pipeline) {
        pipeline.addLast(validateHandler);
        pipeline.addLast(initOrderHandler);
        pipeline.addLast(logisticalHandler);
        pipeline.addLast(clearCartItemHandler);
    }

    @Override
    protected TransConvert createConvert() { //构建转换器
        return new CreateOrderConvert();
    }
}