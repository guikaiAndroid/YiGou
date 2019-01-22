package com.guikai.yigou;

import com.guikai.latte.activities.ProxyActivity;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.launcher.LauncherFragment;
import com.guikai.latte.launcher.launcherScrollFragment;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteFragment setRootFragment() {
        return new launcherScrollFragment();
    }
}
