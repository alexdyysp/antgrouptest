package com.ant.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author daiyuanyang
 * @date 2021-02-19 22:02
 **/
@UtilityClass
@Slf4j
public class FutureUtils {

    public <T> Future<T> createFuture(ExecutorService executor, Supplier<T> supplier) {
        return executor.submit(supplier::get);
    }

    public <T> T getResultOfFuture(Future<T> future, T defaultT, int defaultTimeout) throws TimeoutException, ExecutionException, InterruptedException {
        if (future == null) {
            return defaultT;
        }
        try {
            return future.get(defaultTimeout, TimeUnit.MILLISECONDS);
        } catch (CancellationException e) {
            // 什么也不做，丢弃线程的时候已经打印日志了
        } catch (TimeoutException e) {
            log.error("func=getFromFuture timeout = {}", defaultTimeout);
            throw e;
        } catch (Exception e) {
            log.error("func=getFromFuture default={}", defaultT, e);
            throw e;
        }
        return defaultT;
    }

    /**
     * 指定时间内批量获取future结果
     * @param futures
     * @param defaultV
     * @param timeout 整体超时时间
     * @param <T>
     * @return
     */
    public <T> List<T> getResultOfFutures(List<Future<T>> futures, T defaultV, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        if (futures == null || futures.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<T> result = new ArrayList<>(futures.size());
        long start = System.currentTimeMillis();
        for (Future<T> future : futures) {
            long spendTime = System.currentTimeMillis() - start;
            long leftTime = timeout - spendTime;
            result.add(getResultOfFuture(future, defaultV, (int) leftTime));
        }
        return result;
    }

}