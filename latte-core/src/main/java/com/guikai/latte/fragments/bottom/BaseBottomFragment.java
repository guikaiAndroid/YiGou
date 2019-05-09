package com.guikai.latte.fragments.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.guikai.latte.R;
import com.guikai.latte.fragments.LatteFragment;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Anding on 2019/1/27 14:28
 * Note:
 */

public abstract class BaseBottomFragment extends LatteFragment implements View.OnClickListener {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemFragment> ITEM_FRAGMENTS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap<>();

    private int mCurrentFragment = 0;
    private int mIndexFragment = 0;
    private int mClickedColor = Color.RED;
    private LinearLayoutCompat mBottomBar = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_bottom;
    }

    public ArrayList<BottomItemFragment> getItemFragments() {
        return ITEM_FRAGMENTS;
    }

    public abstract LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(BottomItemBuilder builder);

    public abstract int setIndexFragment();

    @ColorInt
    public abstract int setClickColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexFragment = setIndexFragment();
        if (setClickColor() != 0) {
            mClickedColor = setClickColor();
        }

        final BottomItemBuilder builder = BottomItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemFragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemFragment value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_FRAGMENTS.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mBottomBar = $(R.id.bottom_bar);
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            //填充底部导航按钮
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化底部数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexFragment) {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        final ISupportFragment[] delegateArray = ITEM_FRAGMENTS.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexFragment, delegateArray);
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    public void changeColor(int tabIndex) {
        resetColor();
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(tabIndex);
        //获取底部icon
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        //获取底部导航文字
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
    }

    @Override
    public void onClick(View view) {
        final int tabIndex = (int) view.getTag();
        if (tabIndex != mCurrentFragment) {
            changeColor(tabIndex);
            getSupportDelegate().showHideFragment(ITEM_FRAGMENTS.get(tabIndex), ITEM_FRAGMENTS.get(mCurrentFragment));
            //注意先后顺序
            mCurrentFragment = tabIndex;
        }
    }

    public void setCurrentFragment(int currentFragment) {
        mCurrentFragment = currentFragment;
    }
}
