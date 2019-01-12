package com.guikai.yigou;

import com.guikai.latte.activities.ProxyActivity;
import com.guikai.latte.fragments.LatteFragment;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteFragment setRootFragment() {
        return new MainFragment();
    }
}
