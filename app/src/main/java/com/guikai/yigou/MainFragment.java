package com.guikai.yigou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.IError;
import com.guikai.latte.net.callback.IFailure;
import com.guikai.latte.net.callback.ISuccess;

/**
 * Created by Anding on 2019/1/12 19:34
 * Note:
 */

public class MainFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

    private void testRestClient() {
        RestClient.builder()
                .url("")
                .params("" , "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build();
    }
}
