package com.model.fragmentmanager.tools;

import com.model.fragmentmanager.activity.ParasitismActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Activity Stack manager tools
 *
 * @author SongWenjun
 * @since 2022-06-16
 */
public class ActivityStackListManager {
    //所有打开的Activity
    private static final Stack<ParasitismActivity> activityStack = new Stack<>();
    private static final Map<String, ParasitismActivity> activityMap = new HashMap<>();

    private static volatile ActivityStackListManager activityStackListManager;

    public Stack<ParasitismActivity> getActivityStack() {
        return activityStack;
    }

    private ActivityStackListManager() {
    }

    public static ActivityStackListManager getInstance() {
        if (activityStackListManager == null) {
            synchronized (ActivityStackListManager.class) {
                if (activityStackListManager == null) {
                    activityStackListManager = new ActivityStackListManager();
                }
            }
        }
        return activityStackListManager;
    }

    /**
     * 添加Activity并打标记
     *
     * @param activity activity
     */
    public synchronized void addActivityStack(ParasitismActivity activity) {
        String key = RandomStringTool.randomUpperCaseStr(10, true);
        ParasitismActivity parasitismActivity = activityMap.get(key);
        if (parasitismActivity == null) {
            activityStack.add(activity);
            activityMap.put(key, activity);
        } else {
            addActivityStack(activity);
        }
    }

    public synchronized void removeActivityStack(ParasitismActivity activity) {
        activityStack.remove(activity);
        activityMap.remove(activity.getActivityTag());
    }

    public synchronized ParasitismActivity getCurrentActivity() {
        if (activityStack.size() == 0) {
            throw new RuntimeException("not have fragment is opening");
        }
        return activityStack.get(activityStack.size() - 1);
    }
}
