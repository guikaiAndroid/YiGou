package com.guikai.latte.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latteec.R;

import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class AddressFragment extends LatteFragment implements ISuccess {

    private RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_address);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        mRecyclerView = $(R.id.rv_address);
        RestClient.builder()
                .url("address.php")
//                .loader(getContext())
                .success(this)
                .build()
                .get();
    }


    @Override
    public void onSuccess(String response) {
        LogUtils.d("AddressFragment",response);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter adapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
