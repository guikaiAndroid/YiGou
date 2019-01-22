package com.guikai.latte.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.util.timer.BaseTimerTask;
import com.guikai.latte.util.timer.ITimerListener;
import com.guikai.latteec.R;

import java.text.MessageFormat;
import java.util.Timer;

public class LauncherFragment extends LatteFragment implements ITimerListener {

    public AppCompatTextView mTvTimer = null;
    private Timer mTimer = null;
    private int mCount = 5;

    private void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0 , 1000);
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        initTimer();
        mTvTimer = $(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTimerView();
            }
        });
    }


    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });
    }
}
