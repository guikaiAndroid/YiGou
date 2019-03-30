package com.guikai.latte.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.guikai.latte.detail.GoodsDetailFragment;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;

public class IndexItemClickListener extends SimpleClickListener {

    private final LatteFragment FRAGMENT;

    private IndexItemClickListener(LatteFragment fragment) {
        this.FRAGMENT = fragment;
    }

    public static SimpleClickListener create(LatteFragment fragment) {
        return new IndexItemClickListener(fragment);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailFragment fragment = GoodsDetailFragment.create(1);
        FRAGMENT.getSupportDelegate().start(fragment);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
