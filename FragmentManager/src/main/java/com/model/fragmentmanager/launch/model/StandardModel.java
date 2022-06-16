package com.model.fragmentmanager.launch.model;

import android.os.Bundle;

import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;
import com.model.fragmentmanager.tools.ActivityStackListManager;

import java.util.Map;

/**
 * 标准启动模式
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class StandardModel implements ILunchModelStart {
    @Override
    public void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        ParasitismActivity currentActivity = ActivityStackListManager.getInstance().getCurrentActivity();
        currentActivity.addFragment(fragmentInfo, params);
    }
}
