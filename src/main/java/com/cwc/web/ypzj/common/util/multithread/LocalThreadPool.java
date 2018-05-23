package com.cwc.web.ypzj.common.util.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LocalThreadPool {
    private ExecutorService executor;
    private static LocalThreadPool threadPool;
    private LocalThreadPool(){
        executor= Executors.newFixedThreadPool(20);
    }
    public static LocalThreadPool getInstance() {
        if(threadPool==null){
            synchronized (LocalThreadPool.class){
                if (threadPool==null){
                    return new LocalThreadPool();
                }
            }
        }
        return threadPool;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
