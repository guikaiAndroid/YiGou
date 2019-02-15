package com.guikai.latte.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latteec.R;

public class ContentFragment extends LatteFragment {

    public static final String ARG_CONTENT_ID = "CONTENT_ID";

    public static ContentFragment newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
