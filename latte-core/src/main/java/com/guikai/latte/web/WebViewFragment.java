package com.guikai.latte.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guikai.latte.R;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.web.route.RouteKeys;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class WebViewFragment extends LatteFragment {
    private String mUrl;

    public static WebViewFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(RouteKeys.URL.name());
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_web;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = $(R.id.tb_discover);
        mToolbar.setPadding(0, getStatusBarHeight(),0,0);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebFragmentImpl fragment = WebFragmentImpl.create(mUrl);
//        final WebFragmentImpl fragment = WebFragmentImpl.create("https://www.csdn.net/");
        fragment.setTopFragment(this);
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container,fragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
