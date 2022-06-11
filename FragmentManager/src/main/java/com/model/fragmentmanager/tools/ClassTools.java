package com.model.fragmentmanager.tools;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import dalvik.system.DexFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassTools {
    public static Set<String> getFileNamePackegeName(Application application, String packgage) {
        final Set<String> classNames = new HashSet<>();
        try {
            List<String> soucePaths = getSoucePaths(application);
            for (final String path : soucePaths) {
                DexFile dexFile = null;
                try {
                    dexFile = new DexFile(path);
                    Enumeration<String> entries = dexFile.entries();
                    while (entries.hasMoreElements()) {
                        String className = entries.nextElement();
                        if (className.startsWith(packgage)) {
                            classNames.add(className);
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("router.error->", e.getMessage());
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return classNames;
    }

    private static List<String> getSoucePaths(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitSourceDirs) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitPublicSourceDirs));
            }
        }
        return sourcePaths;
    }
}
