package com.model.fragmentmanager.supper;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.tools.FragmentManager;

import java.util.Map;

/**
 * baseFragment
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class ActivityFragment extends Fragment {

    private String fragmentKey;
    protected ParasitismActivity context;

    private Intent intent;

    public final void addIntent(Intent intent) {
        this.intent = intent;
    }

    public final Intent getIntent() {
        return intent;
    }

    public final void addContext(ParasitismActivity activity) {
        context = activity;
    }

    public final void addKey(String key) {
        if (fragmentKey == null) {
            fragmentKey = key;
        }
    }

    public final String getFragmentKey() {
        return fragmentKey;
    }

    public final void finish() {
        if (context != null) {
            context.finishFragment(this);
        }
    }

    public final void startFragment(Intent intent) {
        FragmentManager.startFragment(intent);
    }

    public void params(Map<String, Object> params) {
    }
}
