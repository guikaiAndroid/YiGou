package com.guikai.latte.main.personal.settings;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.list.ListBean;

public class SettingsClickListener extends SimpleClickListener {

    private final LatteFragment FRAGMENT;

    public SettingsClickListener(LatteFragment fragment) {
        this.FRAGMENT = fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //消息推送的开关
                break;
            case 2:
                FRAGMENT.getSupportDelegate().start(bean.getFragment());
                break;
            default:
                break;
        }
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
