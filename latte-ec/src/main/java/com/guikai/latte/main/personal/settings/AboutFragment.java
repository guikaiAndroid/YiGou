package com.guikai.latte.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latteec.R;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class AboutFragment extends LatteFragment {
    @Override
    public Object setLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        final AppCompatTextView textView = $(R.id.tv_info);
        final Toolbar mToolbar = $(R.id.tb_about);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        RestClient.builder()
                .url("about.json")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        textView.setText(info);
                    }
                })
                .build()
                .get();
    }
}
