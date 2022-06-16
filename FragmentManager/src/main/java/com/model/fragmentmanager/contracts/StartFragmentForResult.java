package com.model.fragmentmanager.contracts;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.model.fragmentmanager.contracts.bean.FragmentResult;

/**
 * 功能描述
 *
 * @author SongWenjun
 * @since 2022-06-15
 */
public class StartFragmentForResult extends ActivityResultContract<Intent, FragmentResult> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Intent input) {
        return input;
    }

    @Override
    public FragmentResult parseResult(int resultCode, @Nullable Intent intent) {
        return new FragmentResult(resultCode, intent);
    }
}
