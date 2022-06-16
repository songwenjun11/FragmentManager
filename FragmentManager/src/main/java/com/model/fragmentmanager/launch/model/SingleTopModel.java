package com.model.fragmentmanager.launch.model;

import static com.model.fragmentmanager.tools.ActivityStackListManager.getInstance;

import android.os.Bundle;

import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;

import java.util.Map;

/**
 * 栈顶复用模式
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class SingleTopModel implements ILunchModelStart {
    @Override
    public void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        String currentFragmentName = getInstance().getCurrentActivity().getCurrentFragmentName();
        String className = fragmentInfo.getClassName();
        if (currentFragmentName.equals(className)) {
            getInstance().getCurrentActivity().finishCurrentFragment();
        }
        getInstance().getCurrentActivity().addFragment(fragmentInfo, params);
    }
}
