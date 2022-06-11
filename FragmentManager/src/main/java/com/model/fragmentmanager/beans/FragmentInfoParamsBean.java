package com.model.fragmentmanager.beans;

import java.util.Map;

/**
 * 类作用
 *
 * @author SongWenjun
 * @since 2022/6/10
 */
public class FragmentInfoParamsBean {
    FragmentInfo info;
    Map<String, Object> params;

    public FragmentInfo getInfo() {
        return info;
    }

    public void setInfo(FragmentInfo info) {
        this.info = info;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
