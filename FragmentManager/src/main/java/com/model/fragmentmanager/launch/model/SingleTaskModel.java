package com.model.fragmentmanager.launch.model;

import android.os.Bundle;

import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;
import com.model.fragmentmanager.tools.ActivityStackListManager;
import com.model.fragmentmanager.tools.FragmentManager;

import java.util.Map;
import java.util.Stack;

/**
 * 站内服用模式
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class SingleTaskModel implements ILunchModelStart {
    @Override
    public void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        Stack<ParasitismActivity> activityStack = ActivityStackListManager.getInstance().getActivityStack();
        int activityIndex = -1;
        for (int i = 0; i < activityStack.size(); i++) {
            ParasitismActivity parasitismActivity = activityStack.get(i);
            if (parasitismActivity != null) {
                if (activityIndex != -1) {
                    parasitismActivity.finish();
                    continue;
                }
                boolean containFragment = parasitismActivity.isContainFragment(fragmentInfo.getClassName());
                if (containFragment) {
                    activityIndex = i;
                }
            }
        }
        ActivityStackListManager.getInstance().getCurrentActivity().reuseAdd(fragmentInfo, params);
    }
}
