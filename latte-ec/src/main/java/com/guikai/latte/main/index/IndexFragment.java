package com.guikai.latte.main.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AndroidException;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.guikai.latte.fragments.bottom.BottomItemFragment;
import com.guikai.latte.main.EcBottomFragment;
import com.guikai.latte.main.index.search.SearchFragment;
import com.guikai.latte.net.RestCreator;
import com.guikai.latte.net.rx.RxRestClient;
import com.guikai.latte.ui.recycler.BaseDecoration;
import com.guikai.latte.ui.refresh.RefreshHandler;
import com.guikai.latte.util.callback.CallbackManager;
import com.guikai.latte.util.callback.CallbackType;
import com.guikai.latte.util.callback.IGlobalCallback;
import com.guikai.latteec.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qiu.niorgai.StatusBarCompat;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Created by Anding on 2019/1/27 18:22
 * Note: 主页fragment
 */

public class IndexFragment extends BottomItemFragment implements View.OnFocusChangeListener {

    private RecyclerView mRecyclerView = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mRecyclerView = $(R.id.rv_index);
        mSwipeRefreshLayout = $(R.id.srl_index);
        Toolbar mToolbar = $(R.id.tb_index);

        final IconTextView mIconScan = $(R.id.icon_index_scan);
        final AppCompatEditText mSearch = $(R.id.et_search_view);

        mToolbar.getBackground().mutate().setAlpha(0);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mRefreshHandler = RefreshHandler.create(mSwipeRefreshLayout, mRecyclerView,
                new IndexDataConverter());
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@NonNull Object args) {
                        ToastUtils.showShort("得到的二维码是"+args);
                    }
                });

        mIconScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanWithCheck(getParentFragments());
            }
        });
        mSearch.setOnFocusChangeListener(this);

//        onCallRxGet();
//        onCallRxRestClient();
    }

    //TODO:第一种RX+Retrofit网络请求测试
    void onCallRxGet() {
        final String url = "index.php";
        final WeakHashMap<String,Object> params = new WeakHashMap<>();

        final Observable<String> observable = RestCreator.getRxRestService().get(url,params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                       ToastUtils.showLong(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO:第二种RX+Retrofit网络请求测试
    private void onCallRxRestClient() {
        final String url = "index.php";
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        ToastUtils.showLong(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwipeRefreshLayout.setProgressViewOffset(true, 120, 260);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        final Context context = getContext();
        mRecyclerView.setLayoutManager(manager);
        if (context != null)
            mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context,
                    R.color.app_background), 5));
        final EcBottomFragment ecBottomFragment = getParentFragments();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomFragment));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        StatusBarCompat.translucentStatusBar(getProxyActivity(), true);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentFragments().start(new SearchFragment());
        }
    }
}
