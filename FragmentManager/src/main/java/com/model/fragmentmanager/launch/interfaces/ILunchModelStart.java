package com.model.fragmentmanager.launch.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.beans.FragmentInfo;

import java.util.Map;

/**
 * 启动方式抽取工具
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public interface ILunchModelStart {

    /**
     * 启动页面
     *
     * @param fragmentInfo fragment实体
     * @param params       参数
     * @param bundle       参数
     */
    void startActivity(FragmentInfo fragmentInfo, Map<String, Object> params, Bundle bundle);

    /**
     * 创建新的任务栈  newInstance
     *
     * @param mContext 上下文
     * @param bundle   参数
     */
    default void newInstance(Context mContext, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, ParasitismActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (mContext instanceof Activity && requestCode != 0) {
            Activity activity = (Activity) mContext;
            activity.startActivityForResult(intent, requestCode);
            return;
        }
        mContext.startActivity(intent);
    }
}
