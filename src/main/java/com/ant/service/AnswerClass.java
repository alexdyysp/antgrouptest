package com.ant.service;

import com.ant.gateway.PaymentRemoteGateway;
import com.ant.gateway.dto.PaymentConsultResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author daiyuanyang
 * @date 2021-02-19 22:31
 **/
@Service
public class AnswerClass {
    @Resource
    private PaymentRemoteGateway paymentRemoteGateway;

    /**
     *  答案接口
     */
    public List<PaymentConsultResult> getAnswerInTime(List<String> payments){
        return paymentRemoteGateway.batchGetPaymentList(payments);
    }
}
