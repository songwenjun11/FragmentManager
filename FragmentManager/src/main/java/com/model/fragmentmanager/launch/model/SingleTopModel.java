package com.model.fragmentmanager.launch.model;

import android.os.Bundle;

import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;

import java.util.Map;

import static com.model.fragmentmanager.tools.FragmentManager.getCurrentActivity;

/**
 * 栈顶复用模式
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class SingleTopModel implements ILunchModelStart {
    @Override
    public void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        String currentFragmentName = getCurrentActivity().getCurrentFragmentName();
        String className = fragmentInfo.getClassName();
        if (currentFragmentName.equals(className)) {
            getCurrentActivity().finishCurrentFragment();
        }
        getCurrentActivity().addFragment(fragmentInfo, params);
    }
}
