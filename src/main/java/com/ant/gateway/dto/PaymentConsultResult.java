package com.ant.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 支付方式列表
 * @author daiyuanyang
 * @date 2021-02-19 22:05
 **/
@Builder
@Data
@AllArgsConstructor
public class PaymentConsultResult {

    /** 支付类型 **/
    private String paymentType;

    /** 咨询结果是否可用*/
    private boolean isEnable;

    /** 错误码 */
    private String errorCode;
}
