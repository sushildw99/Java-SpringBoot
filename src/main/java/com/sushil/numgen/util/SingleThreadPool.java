package com.sushil.numgen.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public enum SingleThreadPool {


    INSTANCE;

    private final ExecutorService service = createSingleThreadPool();

    /**
     * @return
     */
    public ExecutorService getInstance() {
        return service;
    }

    /**
     * @return
     */
    private ExecutorService createSingleThreadPool() {
        return Executors.newSingleThreadExecutor();
    }


    /**
     *
     */
    public void shutdown() {
        if (service != null) {
            service.shutdown();
        }
    }
}
