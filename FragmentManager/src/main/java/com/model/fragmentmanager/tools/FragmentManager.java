package com.model.fragmentmanager.tools;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.note.enums.LaunchMode;
import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.interfaces.IFragmentPut;
import com.model.fragmentmanager.launch.LaunchManager;
import com.model.fragmentmanager.supper.ActivityFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * fragment管理工具
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class FragmentManager {
    public static Application mContext;

    private static final Stack<FragmentInfo> fragmentStack = new Stack<>();//所有注册的fragment
    private static final Map<String, FragmentInfo> fragmentMap = new HashMap<>();
    //所有注册的fragment
    private static final Stack<ParasitismActivity> activityStack = new Stack<>();//所有打开的Activity
    private static final Map<String, ParasitismActivity> activityMap = new HashMap<>();

    public static Map<String, FragmentInfo> getFragmentMap() {
        return fragmentMap;
    }

    public static Stack<ParasitismActivity> getActivityStack() {
        return activityStack;
    }

    public static Stack<FragmentInfo> getFragmentStack() {
        return fragmentStack;
    }

    public static void init(Application context) {
        mContext = context;
        Set<String> fileNamePackegeName = ClassTools.getFileNamePackegeName(context, "fragment.easy.com.");
        for (String className : fileNamePackegeName) {
            try {
                Class<?> aClass = Class.forName(className);
                if (IFragmentPut.class.isAssignableFrom(aClass)) {
                    IFragmentPut iRouter = (IFragmentPut) aClass.newInstance();
                    iRouter.put();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void addActivityStack(ParasitismActivity activity) {
        String key = RandomStringTool.randomUpperCaseStr(10, true);
        ParasitismActivity parasitismActivity = activityMap.get(key);
        if (parasitismActivity == null) {
            activityStack.add(activity);
            activityMap.put(key, activity);
        } else {
            addActivityStack(activity);
        }
    }

    public synchronized static void removeActivityStack(ParasitismActivity activity) {
        activityStack.remove(activity);
        activityMap.remove(activity.getActivityTag());
    }

    public synchronized static ParasitismActivity getCurrentActivity() {
        if (activityStack.size() == 0) {
            throw new RuntimeException("not have fragment is opening");
        }
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 通过注解处理器添加
     *
     * @param action     actions
     * @param launchMode 启动模式
     * @param clazz      className
     */
    public static void addFragment(String action, String launchMode, String clazz) {
        Class<? super ActivityFragment> fragmentClass = null;
        Log.e("asdasdas", clazz);
        try {
            Class<?> aClass = Class.forName(clazz);
            fragmentClass = (Class<? super ActivityFragment>) aClass;
            FragmentInfo fragmentInfo = new FragmentInfo();
            fragmentInfo.setFragmentClass(fragmentClass);
            fragmentInfo.setActions(new String[]{action});
            fragmentInfo.setLaunchMode(launchMode);
            fragmentStack.add(fragmentInfo);
            fragmentMap.put(fragmentClass.getName(), fragmentInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动一个fragment
     *
     * @param intent intent
     */
    public static void startFragment(Intent intent) {
        LaunchManager.getInstance(mContext).startFragment(intent);
    }
}
