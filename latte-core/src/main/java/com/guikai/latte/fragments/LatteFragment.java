package com.guikai.latte.fragments;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Anding on 2019/1/12 16:58
 * Note:
 */

public abstract class LatteFragment extends PermissionCheckFragment {

    @SuppressWarnings("unchecked")
    public <T extends LatteFragment> T getParentFragments() {
        return (T) getParentFragment();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}

