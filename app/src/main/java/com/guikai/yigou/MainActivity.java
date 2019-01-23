package com.guikai.yigou;

import com.guikai.latte.activities.ProxyActivity;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.launcher.LauncherFragment;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteFragment setRootFragment() {
        return new LauncherFragment();
    }
}
