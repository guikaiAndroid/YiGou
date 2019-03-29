package com.guikai.latte.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latte.util.storage.LattePreference;
import com.guikai.latteec.R;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class SearchFragment extends LatteFragment {

    private AppCompatEditText mSearchEdit = null;
    private SearchAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_search;
    }

    //服务器搜索 返回json 然后解析
    private void onClickSearch(String searchItemText) {
        RestClient.builder()
                .url("search.php?key="+ searchItemText)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        saveItem(searchItemText);
                        mSearchEdit.setText("");
                        //展示一些东西
                        //弹出一段话
                    }
                })
                .build()
                .get();
    }

    @SuppressWarnings("unchecked")
    private void saveItem(String item) {
        if (!StringUtils.isEmpty(item) && !StringUtils.isEmpty(item)) {
            List<String> history;
            final String historyStr =
                    LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);
            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        final RecyclerView recyclerView = $(R.id.rv_search);
        final Toolbar mToolbar = $(R.id.tb_search);
        mSearchEdit = $(R.id.et_search_view);
        mSearchEdit.setFocusable(true);
        mSearchEdit.setFocusableInTouchMode(true);
        mSearchEdit.requestFocus();
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        $(R.id.tv_top_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = mSearchEdit.getText().toString();
                onClickSearch(search);
            }
        });
        $(R.id.icon_top_search_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        mAdapter = new SearchAdapter(data);
        recyclerView.setAdapter(mAdapter);

        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20, 20)
                        .color(Color.GRAY)
                        .build();
            }
        });
        recyclerView.addItemDecoration(itemDecoration);
    }
}
