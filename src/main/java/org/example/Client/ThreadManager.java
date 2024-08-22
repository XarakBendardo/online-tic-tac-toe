package org.example.Client;

public class ThreadManager {
    public static void runInNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
