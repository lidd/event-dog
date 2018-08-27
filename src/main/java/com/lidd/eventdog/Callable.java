package com.lidd.eventdog;

public interface Callable<R> {

    void callback(R result);
}
