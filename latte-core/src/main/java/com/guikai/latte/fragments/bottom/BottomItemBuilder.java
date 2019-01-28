package com.guikai.latte.fragments.bottom;

import java.util.LinkedHashMap;

/**
 * Created by Anding on 2019/1/27 14:53
 * Note: fragment和底部导航Item的绑定
 */

public final class BottomItemBuilder {

    private final LinkedHashMap<BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap<>();

    static BottomItemBuilder builder() {
        return new BottomItemBuilder();
    }

    public final BottomItemBuilder addItem(BottomTabBean bean, BottomItemFragment fragment) {
        ITEMS.put(bean, fragment);
        return this;
    }

    public final BottomItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemFragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemFragment> build() {
        return ITEMS;
    }
}
