package com.model.fragmentmanager.beans;

import android.content.Intent;

import androidx.activity.result.ActivityResultCallback;

import com.model.fragmentmanager.contracts.bean.FragmentResult;
import com.model.fragmentmanager.supper.ActivityFragment;
import com.example.note.enums.LaunchMode;

import java.util.Objects;

/**
 * Fragment实体类
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class FragmentInfo {
    private String launchMode;

    private String[] actions;

    private Intent intent;

    private int requestCode;

    private boolean isResult = false;

    private ActivityResultCallback<? super FragmentResult> callback;

    public ActivityResultCallback<? super FragmentResult> getCallback() {
        return callback;
    }

    public void setCallback(
        ActivityResultCallback<? super FragmentResult> callback) {
        this.callback = callback;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    private Class<? super ActivityFragment> fragmentClass;

    public String getClassName() {
        return Objects.requireNonNull(fragmentClass).getName();
    }

    public boolean isContainAction(String action) {
        if (actions == null || action.length() == 0) {
            return false;
        }
        for (String a : actions) {
            if (a.equals(action)) {
                return true;
            }
        }
        return false;
    }

    public String getLaunchMode() {
        return launchMode;
    }

    public void setLaunchMode(String launchMode) {
        this.launchMode = launchMode;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public Class<? super ActivityFragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<? super ActivityFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }
}
