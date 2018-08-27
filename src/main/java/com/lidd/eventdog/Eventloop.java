package com.lidd.eventdog;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Eventloop {

    private Executor executor = Executors.newFixedThreadPool(100);

    private AsyncTaskAdapter[] tasks = new AsyncTaskAdapter[100];

    public void start() {
        for (int i = 0; ; i++) {
            if (i == tasks.length) {
                i = 0;
            }

            AsyncTaskAdapter task = tasks[i];
            if (task == null) {
                continue;
            }

            if (task.isReady()) {
                if (task.setRunning()) {
                    try {
                        executor.execute(task);
                    } catch (Throwable t) {

                    }
                }
            }

            if (task.isFinished()) {
                if (task.setCallbacked()) {
                    try {
                        task.callback(task.getResult());
                    } catch (Throwable t) {

                    }
                }
            }

            if (task.isCallbacked()) {
                tasks[i] = null;
            }
        }
    }


    public void submit(AsyncTaskAdapter task) {
        if (task.isReady()) {
            for (int i = 0; ; i++) {
                if (i == tasks.length) {
                    i = 0;
                }

                if (tasks[i] == null) {
                    tasks[i] = task;
                    return;
                }
            }
        }
    }
}
