package com.model.fragmentmanager.tools;

/**
 * 防抖动
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class Shake {
    private static long lastTime;

    /**
     * 检测时间是否有效
     *
     * @param intervalTime 时间间隔
     * @return true有效
     */
    public static boolean isEventValid(long intervalTime) {
        long nowTime = System.currentTimeMillis();
        if (lastTime != nowTime) {
            long time = nowTime - lastTime;
            if (intervalTime < time) {
                lastTime = nowTime;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isEventValid() {
        return isEventValid(2000);
    }
}
