package com.model.fragmentmanager.launch.model;

import android.os.Bundle;

import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.beans.FragmentInfoParamsBean;
import com.model.fragmentmanager.event_bus.EventMessage;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;
import com.model.fragmentmanager.tools.FragmentManager;
import com.model.fragmentmanager.tools.Shake;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * 全局唯一模式
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class SingleInstanceModel implements ILunchModelStart {

    private FragmentInfo fragmentInfo;
    private Map<String, Object> params;

    @Override
    public void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        this.fragmentInfo = fragmentInfo;
        this.params = params;
        EventBus.getDefault().register(this);
        if (FragmentManager.getActivityStack().size() == 0) {
            int openFragmentSize = FragmentManager.getCurrentActivity().getOpenFragmentSize();
            if (openFragmentSize == 0) {
                FragmentManager.getCurrentActivity().addFragment(this.fragmentInfo, this.params);
                return;
            }
        }
        startNewActivity(FragmentManager.mContext, bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addToActivity(EventMessage eventMessage) {
        if (!Shake.isEventValid()) {
            return;
        }
        FragmentManager.getCurrentActivity().addFragment(fragmentInfo, params);
        EventBus.getDefault().unregister(this);
    }
}
