package com.model.fragmentmanager.tools;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;

import com.example.note.enums.LaunchMode;
import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.contracts.bean.FragmentResult;
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
    private static volatile FragmentManager fragmentManager;

    public static Application mContext;

    private final Stack<FragmentInfo> fragmentStack = new Stack<>();//所有注册的fragment
    private final Map<String, FragmentInfo> fragmentMap = new HashMap<>();//所有注册的fragment

    public Map<String, FragmentInfo> getFragmentMap() {
        return fragmentMap;
    }

    public Stack<FragmentInfo> getFragmentStack() {
        return fragmentStack;
    }

    private FragmentManager() {
    }

    public static FragmentManager getInstance() {
        if (fragmentManager == null) {
            synchronized (FragmentManager.class) {
                if (fragmentManager == null) {
                    fragmentManager = new FragmentManager();
                }
            }
        }
        return fragmentManager;
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

    /**
     * 通过注解处理器添加
     *
     * @param action     actions
     * @param launchMode 启动模式
     * @param clazz      className
     */
    public void addFragment(String action, String launchMode, String clazz) {
        Class<? super ActivityFragment> fragmentClass = null;
        Log.e("asdasdas", clazz);
        try {
            Class<?> aClass = Class.forName(clazz);
            fragmentClass = (Class<? super ActivityFragment>) aClass;
            FragmentInfo fragmentInfo = new FragmentInfo();
            fragmentInfo.setFragmentClass(fragmentClass);
            fragmentInfo.setActions(new String[] {action});
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
    public void startFragment(Intent intent) {
        LaunchManager.getInstance(mContext).startFragment(intent);
    }

    public void startFragmentForResult(Intent intent, int requestCode) {
        LaunchManager.getInstance(mContext).startFragment(intent, requestCode, null);
    }

    public void launcherFragment(Intent intent, int requestCode,
                                        ActivityResultCallback<? super FragmentResult> callback) {
        LaunchManager.getInstance(mContext).startFragment(intent, requestCode, callback);
    }
}
