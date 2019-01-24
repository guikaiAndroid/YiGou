package com.guikai.yigou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.guikai.latte.activities.ProxyActivity;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.launcher.LauncherFragment;
import com.guikai.latte.sign.SignInFragment;
import com.guikai.latte.sign.SignUpFragment;

public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.hide();
        }
    }

    @Override
    public LatteFragment setRootFragment() {
        return new SignInFragment();
    }
}
