package com.lidd.eventdog;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AsyncTask<REQUEST, RESPONSE> extends Thread{

    private final int NEW = 0;

    private final int READY = 1;

    private final int RUNNING = 2;

    private final int FINISHED = 3;

    private final int CALLBACKED = 4;

    private AtomicInteger status = new AtomicInteger(NEW);

    private AtomicReference<RESPONSE> result = new AtomicReference<RESPONSE>(null);

    private REQUEST request;




    public AsyncTask(REQUEST request) {
        this.request = request;
    }

    public abstract RESPONSE execute(REQUEST request);

    public abstract void callback(RESPONSE response);

    @Override
    public void run() {
        RESPONSE response = execute(request);
        if (result.compareAndSet(null, response)) {
            setFinished();
        }
    }

    public RESPONSE getResult(){
        return result.get();
    }

    public boolean setFinished(){
        return status.compareAndSet(RUNNING, FINISHED);
    }

    public boolean setRunning(){
        return status.compareAndSet(READY, RUNNING);
    }

    public boolean setReady(){
        return status.compareAndSet(NEW, READY);
    }

    public boolean setCallbacked(){
        return status.compareAndSet(FINISHED, CALLBACKED);
    }

    public boolean isFinished(){
        return status.get() >= FINISHED;
    }

    public boolean isCallbacked(){
        return status.get() == CALLBACKED;
    }

    public boolean isReady(){
        return status.get() == READY;
    }
}
