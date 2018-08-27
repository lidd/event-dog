package com.lidd.eventdog;

public interface AsyncStatus {

    boolean isFinished();

    boolean isCallbacked();

    boolean isRunning();

    boolean isReady();

    boolean setFinished();

    boolean setCallbacked();

    boolean setReady();

    boolean setRunning();
}
