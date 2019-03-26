package com.guikai.latte.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.ui.widget.StarLayout;
import com.guikai.latteec.R;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class OrderCommentFragment extends LatteFragment {

    private StarLayout mStarLayout = null;

    void onClickSubmit() {
        ToastUtils.showShort("评分：" + mStarLayout.getStarCount());
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_order_comment);
        mStarLayout = $(R.id.custom_star_layout);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        $(R.id.top_tv_comment_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit();
            }
        });
    }
}
