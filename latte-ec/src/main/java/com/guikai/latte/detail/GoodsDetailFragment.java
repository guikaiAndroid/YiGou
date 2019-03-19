package com.guikai.latte.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latteec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Anding on 2019/2/13 21:42
 * Note:
 */

public class GoodsDetailFragment extends LatteFragment {

    public static GoodsDetailFragment create() {
        return new GoodsDetailFragment();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

}
