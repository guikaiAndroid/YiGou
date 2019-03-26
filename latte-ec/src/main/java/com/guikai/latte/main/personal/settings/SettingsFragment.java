package com.guikai.latte.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.address.AddressFragment;
import com.guikai.latte.main.personal.list.ListBean;
import com.guikai.latte.main.personal.list.ListItemType;
import com.guikai.latteec.R;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class SettingsFragment extends LatteFragment {



    @Override
    public Object setLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        final Toolbar mToolbar = $(R.id.tb_settings);
        final RecyclerView mRecyclerView = $(R.id.rv_address);

        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

//        final ListBean push = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_SWITCH)
//                .setId(1)
//                .setFragment(new AddressFragment())

    }
}
