package com.guikai.latte.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.guikai.latte.app.AccountManager;
import com.guikai.latte.app.IUserChecker;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.ui.launcher.ILauncherListener;
import com.guikai.latte.ui.launcher.OnLauncherFinishTag;
import com.guikai.latte.ui.launcher.ScrollLauncherTag;
import com.guikai.latte.util.storage.LattePreference;
import com.guikai.latte.util.timer.BaseTimerTask;
import com.guikai.latte.util.timer.ITimerListener;
import com.guikai.latteec.R;

import java.text.MessageFormat;
import java.util.Timer;

public class LauncherFragment extends LatteFragment implements ITimerListener {

    public AppCompatTextView mTvTimer = null;
    private Timer mTimer = null;
    private int mCount = 5;
    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    private void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        initTimer();
        mTvTimer = $(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(v -> onClickTimerView());
    }

    public void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().startWithPop(new LauncherScrollFragment());
        } else {
            //检查用户是否登录了
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过 {0}s", mCount));
                    mCount--;
                    if (mCount < 1) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            //判断是否第一次登录
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        return true;
    }

}
