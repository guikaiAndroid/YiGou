package com.guikai.latte.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latteec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class OrderListFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_order_list);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
