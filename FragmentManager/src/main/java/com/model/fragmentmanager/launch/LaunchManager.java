package com.model.fragmentmanager.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.note.enums.LaunchMode;
import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.launch.interfaces.ICustomLaunchModel;
import com.model.fragmentmanager.launch.interfaces.ILunchModelStart;
import com.model.fragmentmanager.launch.model.SingleInstanceModel;
import com.model.fragmentmanager.launch.model.SingleTaskModel;
import com.model.fragmentmanager.launch.model.SingleTopModel;
import com.model.fragmentmanager.launch.model.StandardModel;
import com.model.fragmentmanager.tools.FragmentManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 启动模式管理
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class LaunchManager {
    private Context context;

    private volatile static LaunchManager launchManager;

    private ICustomLaunchModel iCustomLaunchModel;

    public void setiCustomLaunchModel(ICustomLaunchModel iCustomLaunchModel) {
        this.iCustomLaunchModel = iCustomLaunchModel;
    }

    private LaunchManager(Context context) {
        this.context = context;
    }

    public static LaunchManager getInstance(Context context) {
        if (launchManager == null) {
            synchronized (LaunchManager.class) {
                if (launchManager == null) {
                    launchManager = new LaunchManager(context);
                }
            }
        }
        return launchManager;
    }

    public void startFragment(Intent intent) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        Map<String, Object> params = extractParams(intent);
        if (action == null || action.equals("")) {
            //class对象的启动方式
            String packageName = intent.getComponent().getClassName();
            FragmentInfo fragmentInfo = FragmentManager.getFragmentMap().get(packageName);
            if (fragmentInfo == null) {
                throw new NullPointerException("Fragment is not fount");
            }
            fragmentInfo.setIntent(intent);
            startActivityForClass(fragmentInfo, params, bundle);
        } else {
            //action的启动方式
            startActivityForAction(action, params, bundle);
        }
    }

    private void startActivityForAction(String action, Map<String, Object> params, Bundle bundle) {
        boolean containAction = false;
        for (FragmentInfo fragmentInfo : FragmentManager.getFragmentStack()) {
            containAction = fragmentInfo.isContainAction(action);
            if (containAction) {
                startActivityForClass(fragmentInfo, params, bundle);
                break;
            }
        }
        if (!containAction) {
            return;
        }
        throw new NullPointerException("this action is not found");
    }

    private void startActivityForClass(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        String launchMode = fragmentInfo.getLaunchMode();
        if (FragmentManager.getActivityStack().size() == 0) {
            startParasitismActivity(bundle);
        }
        if (!launchMode.equals(LaunchMode.STANDARD)) {
            switch (launchMode) {
                case LaunchMode.SINGLE_INSTANCE:
                    builder(new SingleInstanceModel(), fragmentInfo, params, bundle);
                    break;
                case LaunchMode.SINGLE_TASK:
                    builder(new SingleTaskModel(), fragmentInfo, params, bundle);
                    break;
                case LaunchMode.SINGLE_TOP:
                    builder(new SingleTopModel(), fragmentInfo, params, bundle);
                    break;
                default:
                    //自定义启动模式
                    if (iCustomLaunchModel != null) {
                        ILunchModelStart lunchModelStart = iCustomLaunchModel.bundle(launchMode);
                        builder(lunchModelStart, fragmentInfo, params, bundle);
                    }
                    break;
            }
        } else {
            builder(new StandardModel(), fragmentInfo, params, bundle);
        }
    }

    private void builder(ILunchModelStart lunchModelStart, FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle) {
        lunchModelStart.startActivity(fragmentInfo, params, bundle);
    }

    /**
     * 提取intent中的参数
     *
     * @param intent 需要提取的intent
     * @return 参数Map
     */
    private static Map<String, Object> extractParams(Intent intent) {
        Bundle extras = intent.getExtras();
        Map<String, Object> params = new HashMap<>();
        if (extras != null) {
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                params.put(key, extras.get(key));
            }
        }
        return params;
    }

    private void startParasitismActivity(Bundle bundle) {
        Intent intent = new Intent(context, ParasitismActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
