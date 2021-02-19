package com.ant.rpc;

/**
 * 支付方式可用性咨询接口定义：
 * @author daiyuanyang
 * @date 2021-02-19 00:28
 **/
public interface PaymentRemoteSerivce {
    ConsultResult isEnabled(String paymentType);
}
