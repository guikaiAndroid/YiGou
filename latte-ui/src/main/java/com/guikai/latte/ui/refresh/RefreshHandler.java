package com.guikai.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guikai.latte.app.Latte;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.IError;
import com.guikai.latte.net.callback.IFailure;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.DataConverter;
import com.guikai.latte.ui.recycler.MultipleRecyclerAdapter;

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView, DataConverter dataConverter, PagingBean bean) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = dataConverter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    //加载首页数据
    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.d("首页数据" + response);
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    //分页
    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageSize();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd();
        } else {
            Latte.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url + index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    LogUtils.json("上滑分页刷新第" + BEAN.getPageIndex() + "页", response);
                                    mAdapter.setNewData(CONVERTER.setJsonData(response).convert());
                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .failure(new IFailure() {
                                @Override
                                public void onFailure() {
                                    mAdapter.loadMoreFail();
                                    ToastUtils.showShort("数据加载失败，请稍后再试");
                                }
                            })
                            .error(new IError() {
                                @Override
                                public void onError(int code, String msg) {
                                    mAdapter.loadMoreFail();
                                    ToastUtils.showShort("数据加载失败，错误码"+code);
                                }
                            })
                            .build()
                            .get();
                }
            }, 0);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        paging("index_2_data.json?index=");
//        mAdapter.loadMoreEnd();
//        Latte.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RestClient.builder()
//                        .url("index_2_data.json")
//                        .success(new ISuccess() {
//                            @Override
//                            public void onSuccess(String response) {
//                                mAdapter.setNewData(CONVERTER.setJsonData(response).convert());
//                                mAdapter.closeLoadAnimation();
//                                mAdapter.loadMoreComplete();
//                            }
//                        })
//                        .build()
//                        .get();
//            }
//        },1000);
    }
}
