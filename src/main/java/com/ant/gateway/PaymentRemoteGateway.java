package com.ant.gateway;

import com.ant.gateway.dto.PaymentConsultResult;

import java.util.List;

/**
 * @author daiyuanyang
 * @date 2021-02-19 00:37
 **/
public interface PaymentRemoteGateway {

    /**
     * 获取支付可用性列表
     * @param  payments 支付方式参数
     * @return 支付列表
     */
    List<PaymentConsultResult> batchGetPaymentList(List<String> payments);
}
