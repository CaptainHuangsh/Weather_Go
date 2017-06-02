package com.example.owen.weathergo.common;

/**
 * Created by owen on 2017/4/8.
 * 此类用于检测双击退出时间范围
 */

public class DoubleClickExit {
    /**
     * 双击退出检测, 阈值 1000ms
     */
    private static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;// 1000ms

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }

}
