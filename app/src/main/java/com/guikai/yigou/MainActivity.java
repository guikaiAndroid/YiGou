package com.guikai.yigou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.guikai.latte.activities.ProxyActivity;
import com.guikai.latte.app.Latte;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.launcher.LauncherFragment;
import com.guikai.latte.main.EcBottomFragment;
import com.guikai.latte.sign.ISignListener;
import com.guikai.latte.sign.SignInFragment;
import com.guikai.latte.ui.launcher.ILauncherListener;
import com.guikai.latte.ui.launcher.OnLauncherFinishTag;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity implements
        ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public LatteFragment setRootFragment() {
        return new LauncherFragment();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
//                Toast.makeText(this, "您已登陆了哟！", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new EcBottomFragment());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "亲，您还没有登录！", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new EcBottomFragment());
                break;
            default:
                break;
        }
    }
}
