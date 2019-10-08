package com.github.sisyphsu.nakedata.utils;

import java.lang.management.ManagementFactory;

/**
 * Some time utils for convenience or performance
 *
 * @author sulin
 * @since 2019-10-08 21:02:56
 */
public final class TimeUtils {

    public static        int  INTERVAL = 500;
    private static final long UP_TIME  = ManagementFactory.getRuntimeMXBean().getStartTime();

    private static long now;
    private static long uptime;

    static {
        Thread thread = new Thread(() -> {
            while (true) {
                flush();
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.setName("TimeUtils-Timer");
        thread.setDaemon(true);
        thread.start();
        flush();
    }

    private TimeUtils() {
    }

    /**
     * Fetch current second from 1970.1.1, this is faster and fuzzier
     *
     * @return Now second
     */
    public static long fastNow() {
        return now;
    }

    /**
     * Fetch current second from uptime, this is faster and fuzzier
     *
     * @return Uptime second
     */
    public static long fastUpTime() {
        return uptime;
    }

    private static void flush() {
        long ms = System.currentTimeMillis();
        now = ms;
        uptime = ms - UP_TIME;
    }

}
