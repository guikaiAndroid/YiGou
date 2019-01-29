package com.guikai.latte.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class HolderCreator implements CBViewHolderCreator<ImageHolder> {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
