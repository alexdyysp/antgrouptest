package com.ant.gateway.impl;

import com.ant.gateway.dto.PaymentConsultResult;
import com.ant.rpc.ConsultResult;
import com.ant.gateway.PaymentRemoteGateway;
import com.ant.rpc.PaymentRemoteSerivce;
import com.ant.utils.ConcurrentRpc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyuanyang
 * @date 2021-02-19 00:30
 **/
public class PaymentRemoteGatewayImpl extends ConcurrentRpc<PaymentConsultResult> implements PaymentRemoteGateway {

    @Resource
    private PaymentRemoteSerivce paymentRemoteSerivce;

    @Override
    public List<PaymentConsultResult> batchGetPaymentList(List<String> payments) {
        return concurrentRpcCall(payments.stream()
                .map(s -> IsEnableParam.builder().paymentType(s).build())
                .collect(Collectors.toList()));
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    public static class IsEnableParam extends BaseRpcParam {
        private String paymentType;
    }

    @Override
    protected PaymentConsultResult queryRpc(BaseRpcParam param) {
        IsEnableParam isEnableParam = (IsEnableParam) param;
        ConsultResult result = paymentRemoteSerivce.isEnabled(isEnableParam.getPaymentType());
        return PaymentConsultResult.builder()
                .paymentType(isEnableParam.getPaymentType())
                .isEnable(result.getIsEnable())
                .errorCode(result.getErrorCode())
                .build();
    }

    @Override
    protected Integer getTimeout() {
        return 2;
    }
}
