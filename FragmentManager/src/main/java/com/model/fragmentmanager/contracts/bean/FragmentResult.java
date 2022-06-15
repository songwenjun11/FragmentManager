package com.model.fragmentmanager.contracts.bean;

import android.content.Intent;

/**
 * 返回值实体
 *
 * @author SongWenjun
 * @since 2022-06-15
 */
public class FragmentResult {
    private int requestCode;
    private int resultCode;
    private Intent data;

    public FragmentResult(int requestCode, Intent data) {
        this.requestCode = requestCode;
        this.data = data;
    }

    public FragmentResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
