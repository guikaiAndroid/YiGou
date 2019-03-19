package com.guikai.latte.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.PersonalFragment;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latteec.R;

import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class OrderListFragment extends LatteFragment {

    private String mType = null;
    private RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mType = args.getString(PersonalFragment.ORDER_TYPE);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_order_list);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        mRecyclerView = $(R.id.rv_order_list);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("order_list.php")
                .params("type", mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.d(response);
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListFragment.this));
                    }
                })
                .build()
                .get();
    }
}
