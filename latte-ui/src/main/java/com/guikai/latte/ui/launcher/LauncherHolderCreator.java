package com.guikai.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.guikai.latte.ui.launcher.LauncherHolder;

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {

    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
