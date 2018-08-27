package com.lidd.eventdog;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AsyncTaskAdapter<P, R> extends Thread implements Excutable<P, R>, Callable<R>, AsyncStatus {

    private final int NEW = 0;

    private final int READY = 1;

    private final int RUNNING = 2;

    private final int FINISHED = 3;

    private final int CALLBACKED = 4;

    private AtomicInteger status = new AtomicInteger(NEW);

    private AtomicReference<R> result = new AtomicReference<>(null);

    private P param;

    public AsyncTaskAdapter(P param) {
        this.param = param;
    }

    public abstract R execute(P p);

    public abstract void callback(R r);

    @Override
    public void run() {
        R r = execute(param);
        if (result.compareAndSet(null, r)) {
            setFinished();
        }
    }

    public R getResult() {
        return result.get();
    }

    @Override
    public boolean setFinished() {
        return status.compareAndSet(RUNNING, FINISHED);
    }

    @Override
    public boolean setRunning() {
        return status.compareAndSet(READY, RUNNING);
    }

    @Override
    public boolean setReady() {
        return status.compareAndSet(NEW, READY);
    }

    @Override
    public boolean setCallbacked() {
        return status.compareAndSet(FINISHED, CALLBACKED);
    }

    @Override
    public boolean isFinished() {
        return status.get() >= FINISHED;
    }

    @Override
    public boolean isCallbacked() {
        return status.get() == CALLBACKED;
    }

    @Override
    public boolean isReady() {
        return status.get() == READY;
    }

    @Override
    public boolean isRunning() {
        return status.get() == RUNNING;
    }
}
