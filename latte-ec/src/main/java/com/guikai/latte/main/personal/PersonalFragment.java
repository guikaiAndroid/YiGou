package com.guikai.latte.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latte.main.personal.address.AddressFragment;
import com.guikai.latte.main.personal.list.ListAdapter;
import com.guikai.latte.main.personal.list.ListBean;
import com.guikai.latte.main.personal.list.ListItemType;
import com.guikai.latte.main.personal.order.OrderListFragment;
import com.guikai.latte.main.personal.settings.SettingsFragment;
import com.guikai.latteec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anding on 2019/1/27 18:22
 * Note:
 */

public class PersonalFragment extends BottomItemFragment {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    private void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE,"all");
        startOrderListByType();
    }

    private void startOrderListByType() {
        final OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(mArgs);
        getParentFragments().getSupportDelegate().start(fragment);
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

        final RecyclerView rvSettings = $(R.id.rv_personal_setting);
        $(R.id.tv_all_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAllOrder();
            }
        });
        $(R.id.img_user_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAvatar();
            }
        });

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setFragment(new AddressFragment())
                .setText("收获地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setFragment(new SettingsFragment())
                .setText("系统设置")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        rvSettings.setAdapter(adapter);
        rvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }

    private void onClickAvatar() {

    }
}
