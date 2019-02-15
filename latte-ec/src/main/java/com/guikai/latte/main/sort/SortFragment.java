package com.guikai.latte.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latte.main.sort.content.ContentFragment;
import com.guikai.latte.main.sort.list.VerticalListFragment;
import com.guikai.latteec.R;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Created by Anding on 2019/1/27 18:47
 * Note:
 */

public class SortFragment extends BottomItemFragment {
    @Override
    public Object setLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_sort);
        mToolbar.setPadding(0, getStatusBarHeight(),0,0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListFragment listFragment = new VerticalListFragment();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listFragment);
        //设置右侧fragment第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container,ContentFragment.newInstance(1));
    }
}
