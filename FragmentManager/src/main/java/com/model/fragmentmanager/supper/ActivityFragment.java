package com.model.fragmentmanager.supper;

import android.content.Intent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.model.fragmentmanager.activity.ParasitismActivity;
import com.model.fragmentmanager.contracts.bean.FragmentResult;
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
    private boolean isResult = false;//是否携带返回值
    private int requestCode = 0;//0默认

    private Intent intent;
    private ActivityResultCallback<? super FragmentResult> callback;

    public final void addCallBack(ActivityResultCallback<? super FragmentResult> callback) {
        this.callback = callback;
    }

    public final void addIsResult(boolean isResult) {
        this.isResult = isResult;
    }

    public final void addRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

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

    /**
     * 携带返回值得返回
     *
     * @param intent     intent
     * @param resultCode 响应码
     */
    public final void finishResult(Intent intent, int resultCode) {
        if (context != null && isResult) {
            context.finishFragment(this, requestCode, resultCode, intent);
        }
    }

    public final void startFragment(Intent intent) {
        FragmentManager.startFragment(intent);
    }

    public final void startFragmentForResult(Intent intent, int requestCode) {
        FragmentManager.startFragmentForResult(intent, requestCode);
    }

    public final void startFragmentForResult(Intent intent, int requestCode,
                                             ActivityResultCallback<? super FragmentResult> callback) {
        FragmentManager.launcherFragment(intent, requestCode, callback);
    }

    public final <I extends Intent, O> ActivityResultLauncher<I> registerForFragmentResult(
        @NonNull ActivityResultContract<I, O> contract,
        @NonNull ActivityResultCallback<? super FragmentResult> callback) {
        return registerForFragmentResult(contract, null, callback);
    }

    private <I extends Intent, O> ActivityResultLauncher<I> registerForFragmentResult(
        @NonNull ActivityResultContract<I, O> contract,
        ActivityResultRegistry activityResultRegistry,
        @NonNull ActivityResultCallback<? super FragmentResult> callback) {
        return new ActivityResultLauncher<I>() {
            private I input;

            @Override
            public void launch(I input, @Nullable ActivityOptionsCompat options) {
                this.input = input;
                startFragmentForResult(intent, requestCode, callback);
            }

            @Override
            public void unregister() {
            }

            @NonNull
            @Override
            public ActivityResultContract<I, ?> getContract() {
                return contract;
            }
        };
    }

    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        if (callback != null) {
            callback.onActivityResult(new FragmentResult(requestCode, resultCode, data));
        }
    }

    public void params(Map<String, Object> params) {
    }
}
