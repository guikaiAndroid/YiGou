package com.guikai.latte.main;

import android.graphics.Color;

import com.guikai.latte.fragments.bottom.BaseBottomFragment;
import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latte.fragments.bottom.BottomTabBean;
import com.guikai.latte.fragments.bottom.BottomItemBuilder;
import com.guikai.latte.main.discover.DiscoverFragment;
import com.guikai.latte.main.index.IndexFragment;
import com.guikai.latte.main.cart.ShopCartFragment;
import com.guikai.latte.main.sort.SortFragment;

import java.util.LinkedHashMap;

/**
 * Created by Anding on 2019/1/27 18:18
 * Note:
 */

public class EcBottomFragment extends BaseBottomFragment{

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"), new IndexFragment());
        items.put(new BottomTabBean("{fa-sort}","分类"), new SortFragment());
        items.put(new BottomTabBean("{fa-compass}","发现"), new DiscoverFragment());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"), new ShopCartFragment());
        items.put(new BottomTabBean("{fa-user}","我的"), new IndexFragment());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.parseColor("#ffff8800");
    }
}
