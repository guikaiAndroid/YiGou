package com.guikai.latte.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.ToastUtils;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.address.AddressFragment;
import com.guikai.latte.main.personal.list.ListAdapter;
import com.guikai.latte.main.personal.list.ListBean;
import com.guikai.latte.main.personal.list.ListItemType;
import com.guikai.latte.util.callback.CallbackManager;
import com.guikai.latte.util.callback.CallbackType;
import com.guikai.latteec.R;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class SettingsFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        final Toolbar mToolbar = $(R.id.tb_settings);
        final RecyclerView mRecyclerView = $(R.id.rv_settings);

        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setFragment(new AddressFragment())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                            ToastUtils.showShort("推送已打开");
                        } else {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                            ToastUtils.showShort("推送已关闭");
                        }
                    }
                })
                .setText("消息推送")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setFragment(new AboutFragment())
                .setText("关于我们")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));

    }
}
