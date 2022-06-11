package com.model.fragmentmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.model.fragmentmanager.beans.FragmentInfo;
import com.model.fragmentmanager.beans.FragmentInfoParamsBean;
import com.model.fragmentmanager.event_bus.EventMessage;
import com.model.fragmentmanager.supper.ActivityFragment;
import com.model.fragmentmanager.tools.FragmentManager;
import com.model.fragmentmanager.tools.RandomStringTool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import static com.model.fragmentmanager.config.KeyConfig.EVENT_ACTIVITY_CREATED;

/**
 * 壳子Activity
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class ParasitismActivity extends AppCompatActivity {

    private int layoutID = new Random().nextInt();
    /**
     * 页面唯一标识
     */
    private volatile String ActivityTag = null;
    private Intent intent;
    private androidx.fragment.app.FragmentManager fragmentManager;

    public synchronized void setActivityTag(String activityTag) {
        synchronized (ParasitismActivity.class) {
            if (this.ActivityTag == null) {
                this.ActivityTag = activityTag;
            }
        }
    }

    public synchronized String getActivityTag() {
        return ActivityTag;
    }

    /**
     * 本页面打开的fragment
     */
    private final Stack<ActivityFragment> fragmentStack = new Stack<>();
    private final List<String> fragmentClassName = new ArrayList<>();
    private final Map<String, ActivityFragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager.addActivityStack(this);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(layoutID);
        setContentView(frameLayout);

        initIntent();
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new EventMessage(EVENT_ACTIVITY_CREATED, "Activity added to stack"));
    }

    private void initIntent() {
        intent = getIntent();
    }

    /**
     * 将class对象转为fragment对象
     *
     * @param fragmentInfo fragmentInfo
     * @return fragment对象
     * @throws IllegalAccessException 错误1
     * @throws InstantiationException 错误2
     */
    public ActivityFragment classToFragment(FragmentInfo fragmentInfo)
            throws IllegalAccessException, InstantiationException {
        Object object = fragmentInfo.getFragmentClass().newInstance();
        if (object instanceof ActivityFragment) {
            ActivityFragment fragment = (ActivityFragment) object;
            fragment.addIntent(fragmentInfo.getIntent());
            return fragment;
        } else {
            throw new NullPointerException(fragmentInfo.getFragmentClass().getName() + " create Instance is null");
        }
    }

    /**
     * 添加一个fragment进行展示
     *
     * @param fragmentInfo 需要展示的
     * @param params       参数
     */
    public void addFragment(FragmentInfo fragmentInfo, Map<String, Object> params) {
        try {
            ActivityFragment fragment = classToFragment(fragmentInfo);
            String fragmentKey = getFragmentKey();
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (ActivityFragment activityFragment : fragmentStack) {
                    activityFragment.onPause();
                    fragmentTransaction.hide(activityFragment);
                    activityFragment.onStop();
                }
                fragmentTransaction.add(layoutID, fragment).show(fragment).commit();
                fragment.addContext(this);
                fragment.params(params);
                fragment.addKey(fragmentKey);
                addStack(fragment);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new NullPointerException("not found " + fragmentInfo.getFragmentClass().getName());
        }
    }

    /**
     * 获取所有fragment的唯一标识
     *
     * @return 唯一标识
     */
    private String getFragmentKey() {
        String random = RandomStringTool.randomUpperCaseStr(10, true);
        ActivityFragment fragment = fragmentMap.get(random);
        if (fragment == null) {
            return random;
        }
        return getFragmentKey();
    }

    /**
     * 栈内复用加载fragment
     * 首先销毁在此之前的fragment
     *
     * @param fragmentInfo fragmentInfo
     * @param params       参数
     */
    public void reuseAdd(FragmentInfo fragmentInfo, Map<String, Object> params) {
        for (int i = 0; i < fragmentStack.size(); i++) {
            String className = fragmentInfo.getClassName();
            String name = fragmentStack.get(i).getClass().getName();
            if (className.equals(name)) {
                finishFragmentIndex(i);
            }
        }
        addFragment(fragmentInfo, params);
    }

    public String getCurrentFragmentName() {
        return getCurrentFragment().getClass().getSimpleName();
    }

    public ActivityFragment getCurrentFragment() {
        return fragmentStack.get(fragmentStack.size() - 1);
    }

    /**
     * 获取任务栈长度
     *
     * @return size
     */
    public int getOpenFragmentSize() {
        return fragmentStack.size();
    }

    /**
     * 获取任务栈中最大下标
     *
     * @return 最大下标
     */
    public int getStakeMaxPosition() {
        return getOpenFragmentSize() - 1;
    }

    public boolean isContainFragment(String className) {
        return fragmentClassName.contains(className);
    }

    public void finishCurrentFragment() {
        finishFragmentIndex(getStakeMaxPosition());
    }

    public void finishFragment(ActivityFragment fragment) {
        fragmentManager.beginTransaction().hide(fragment).remove(fragment).commit();
        if (getOpenFragmentSize() <= 1) {
            finish();
            return;
        }
        removeStack(fragment);
        fragmentManager.beginTransaction().show(fragmentStack.get(getStakeMaxPosition())).commit();
    }

    public void finishFragmentIndex(int index) {
        ActivityFragment fragment = fragmentStack.get(index);
        fragmentManager.beginTransaction().hide(fragment).remove(fragment).commit();
        if (index == 0) {
            finish();
            return;
        }
        fragmentManager.beginTransaction().show(fragmentStack.get(index - 1)).commit();
        removeStack(fragment);
    }

    private void addStack(ActivityFragment activityFragment) {
        fragmentStack.add(activityFragment);
        fragmentClassName.add(activityFragment.getClass().getName());
        fragmentMap.put(activityFragment.getFragmentKey(), activityFragment);
    }

    private void removeStack(ActivityFragment activityFragment) {
        if (activityFragment == null) {
            return;
        }
        activityFragment.onPause();
        fragmentStack.remove(activityFragment);
        fragmentClassName.remove(activityFragment.getClass().getName());
        fragmentMap.remove(activityFragment.getFragmentKey());
        activityFragment.onStop();
        activityFragment.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentManager.removeActivityStack(this);
    }
}
