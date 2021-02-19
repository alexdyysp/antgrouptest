package com.ant.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daiyuanyang
 * @date 2021-02-19 22:07
 **/
@Service
@EnableScheduling
@Slf4j
public class ExecutorFactory {
    private static final AtomicInteger NEXT_ID = new AtomicInteger();

    private int corePoolSize;
    private int maxPoolSize;
    private int queueSize;
    private int keepAliveTime;

    private ThreadPoolExecutor executor;
    //private ThreadPoolExecutorTraceWrapper executorTraceWrapper;

    @PostConstruct
    public void init() {
        corePoolSize =  50;
        maxPoolSize =  100;
        queueSize = 500;
        keepAliveTime = 1;

        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize),
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName("executor-thread-" + NEXT_ID.incrementAndGet());
                    thread.setDaemon(true);
                    return thread;
                },
                (runnable, runner) -> {
                    log.error("func=rejectedExecution executor={} error={}", ExecutorFactory.this, "丢弃线程");
                    if (runnable instanceof Future) {
                        // 丢弃任务时，任务快速失败
                        ((Future) runnable).cancel(false);
                    }
                }
        );
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
