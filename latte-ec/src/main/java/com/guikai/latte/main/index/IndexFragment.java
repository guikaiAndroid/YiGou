package com.guikai.latte.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latteec.R;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * Created by Anding on 2019/1/27 18:22
 * Note:
 */

public class IndexFragment extends BottomItemFragment {

    private RecyclerView mRecyclerView = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mRecyclerView = $(R.id.rv_index);
        mSwipeRefreshLayout = $(R.id.srl_index);

        final IconTextView mIconScan = $(R.id.icon_index_scan);
        final AppCompatEditText mSearch = $(R.id.et_search_view);


    }
}
