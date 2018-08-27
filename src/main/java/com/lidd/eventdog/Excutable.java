package com.lidd.eventdog;

public interface Excutable<P,R> {
    R execute(P param);
}
