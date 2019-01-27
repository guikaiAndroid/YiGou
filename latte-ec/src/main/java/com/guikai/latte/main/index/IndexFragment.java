package com.guikai.latte.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latteec.R;

/**
 * Created by Anding on 2019/1/27 18:22
 * Note:
 */

public class IndexFragment extends BottomItemFragment {
    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
