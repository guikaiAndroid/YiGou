package com.guikai.latte.main.cart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latte.main.EcBottomFragment;
import com.guikai.latte.main.cart.pay.FastPay;
import com.guikai.latte.main.cart.pay.IAlPayResultListener;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.IError;
import com.guikai.latte.net.callback.IFailure;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latteec.R;
import com.joanzapata.iconify.widget.IconTextView;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Created by Anding on 2019/1/27 18:22
 * Note:
 */

public class ShopCartFragment extends BottomItemFragment
        implements ISuccess, ICartItemListener, View.OnClickListener, IAlPayResultListener {

    private ShopCartAdapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;

    private RecyclerView mRecyclerView = null;
    private IconTextView mIconSelectAll = null;
    private ViewStubCompat mStubNoItem = null;
    private AppCompatTextView mTvTotalPrice = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_cart);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        mRecyclerView = $(R.id.rv_shop_cart);
        mIconSelectAll = $(R.id.icon_shop_cart_select_all);
        mStubNoItem = $(R.id.stub_no_item);
        mTvTotalPrice = $(R.id.tv_shop_cart_total_price);
        mIconSelectAll.setTag(1);
        $(R.id.icon_shop_cart_select_all).setOnClickListener(this);
        $(R.id.tv_top_shop_cart_remove_selected).setOnClickListener(this);
        $(R.id.tv_top_shop_cart_clear).setOnClickListener(this);
        $(R.id.tv_shop_cart_pay).setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart_data.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = mStubNoItem.inflate();
            final AppCompatTextView tvToBuy =
                    stubView.findViewById(R.id.tv_stub_to_buy);
            ToastUtils.showShort("你该购物啦！");
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //切换到首页
                    final int indexTab = 0;
                    final EcBottomFragment ecBottomFragment = getParentFragments();
                    ecBottomFragment.setCurrentFragment(0);
                    final BottomItemFragment indexFragment = ecBottomFragment.getItemFragments().get(indexTab);
                    ecBottomFragment
                            .getSupportDelegate()
                            .showHideFragment(indexFragment, ShopCartFragment.this);
                    ecBottomFragment.changeColor(indexTab);
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.icon_shop_cart_select_all) {
            //全选
            onClickSelectAll();

        } else if (i == R.id.tv_top_shop_cart_remove_selected) {
            //删除
            onClickRemoveSelectedItem();

        } else if (i == R.id.tv_top_shop_cart_clear) {
            //清空
            onClickClear();

        } else if (i == R.id.tv_shop_cart_pay) {
            createOrder();
        }
    }

    private void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        if (data.size() != 0) {
            //要删除的数据
            final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
            for (MultipleItemEntity entity : data) {
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                if (isSelected) {
                    deleteEntities.add(entity);
                }
            }
            for (MultipleItemEntity entity : deleteEntities) {
                int removePosition;
                final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
                if (entityPosition > mCurrentCount - 1) {
                    removePosition = entityPosition - (mTotalCount - mCurrentCount);
                } else {
                    removePosition = entityPosition;
                }
                if (removePosition <= mAdapter.getItemCount()) {
                    mAdapter.remove(removePosition);
                    mCurrentCount = mAdapter.getItemCount();
                    //更新数据
                    mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
                }
            }
            checkItemCount();
        } else {
            ToastUtils.showShort("空空如也，赶紧购物吧！");
        }
    }

    private void onClickClear() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        if (data.size() != 0) {
            data.clear();
            mAdapter.notifyDataSetChanged();
            checkItemCount();
        } else {
            ToastUtils.showShort("空空如也，赶紧购物吧！");
        }
    }

    //创建订单，这里和支付是没有关系的 C端发送订单给服务端，服务端响应给C端秘钥
    private void createOrder() {
        final String orderUrl = "服务端拼接订单信息的Url";
//        final String orderUrl = "http://app.api.zanzuanshi.com/api/v1/peyment";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //加入你的订单信息 购物车中的物品等等 封装进orderParams里
//        orderParams.put("userid", 264392);
//        orderParams.put("amount", 0.01);
//        orderParams.put("comment", "测试支付");
//        orderParams.put("type", 1);
//        orderParams.put("ordertype", 0);
//        orderParams.put("isanonymous", true);
//        orderParams.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //获取到服务端数据 调用支付宝SDK 发起支付
                        LogUtils.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartFragment.this)
                                .setPayResultListener(ShopCartFragment.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        ToastUtils.showShort("需要服务端支持！");
                        LogUtils.d("PAY_SIGN", "支付服务端请求失败");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.d("PAY_SIGN", "支付服务端请求错误");
                    }
                })
                .build()
                .post();

    }

    private void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            final Context context = getContext();
            if (context != null) {
                mIconSelectAll.setTextColor
                        (ContextCompat.getColor(context, R.color.app_main));
            }
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
