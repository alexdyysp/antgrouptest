package com.ant.utils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author daiyuanyang
 * @date 2021-02-19 22:20
 **/
@Slf4j
abstract
public class ConcurrentRpc<T> {

    private static final Integer DEFAULT_TIME_OUT_CONCURRENT = 10 * 1000;

    @Resource
    private ExecutorFactory executorFactory;

    /**
     * 请求参数
     */
    public static class BaseRpcParam {
    }


    public List<T> concurrentRpcCall(List<BaseRpcParam> params) {
        List<Future<T>> futures = new ArrayList<>(16);
        for(BaseRpcParam param: params) {
            futures.add(FutureUtils.createFuture(executorFactory.getExecutor(), () -> queryRpc(param)));
        }

        try {
            return FutureUtils.getResultOfFutures(futures, null, getTimeout());
        } catch (Exception e) {
            log.error("[远程调用] 并发调用 Rpc 时异常", e);
            return Collections.emptyList();
        }
    }

    /**
     * 请求单次RPC数据
     * @param param  请求参数
     * @return 分页数据
     */
    protected abstract T queryRpc(BaseRpcParam param);

    /**
     * 获取超时时间
     * @return 自定义超时时间
     */
    protected Integer getTimeout() {
        return DEFAULT_TIME_OUT_CONCURRENT;
    }

}
