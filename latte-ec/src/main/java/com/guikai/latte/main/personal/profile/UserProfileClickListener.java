package com.guikai.latte.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.list.ListBean;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.data.DateDialogUtil;
import com.guikai.latte.util.callback.CallbackManager;
import com.guikai.latte.util.callback.CallbackType;
import com.guikai.latte.util.callback.IGlobalCallback;
import com.guikai.latteec.R;

public class UserProfileClickListener extends SimpleClickListener {

    private final UserProfileFragment FRAGMENT;

    private String[] mGenders = new String[]{"男", "女", "保密"};

    public UserProfileClickListener(UserProfileFragment fragment) {
        this.FRAGMENT = fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
//           开始打开相机或者选择图片 =>  然后裁剪图片  => 产生回调(图片存储的地址URI)
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(@NonNull Uri args) {
                                LogUtils.d("ON_CROP", args);
                                //首先显示裁剪后存储好的图片作为头像
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(FRAGMENT)
                                        .load(args)
                                        .into(avatar);
                                //上传图片到服务器
                                RestClient.builder()
                                        .url(UploadConfig.UPLOAD_IMG)
                                        .loader(FRAGMENT.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                //图片上传返回结果值
                                                LogUtils.d("ON_CROP_UPLOAD", response);
                                                final String path = JSON.parseObject(response).getJSONObject("result")
                                                        .getString("path");

                                                //通知服务器 更新信息
                                                RestClient.builder()
                                                        .url("user_profile.php")
                                                        .params("avatar", path)
                                                        .loader(FRAGMENT.getContext())
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                //获取更新后的用户信息，然后更新本地数据库
                                                                //没有本地数据的APP，每次打开APP都请求API，获取信息
                                                            }
                                                        })
                                                        .build()
                                                        .post();
                                            }
                                        })
                                        .build()
                                        .upload();
                            }
                        });
                FRAGMENT.startCameraWithCheck();
                break;
            case 2:
                final LatteFragment nameFragment = bean.getFragment();
                FRAGMENT.getSupportDelegate().start(nameFragment);
                break;
            case 3:
                getGenderDiglog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(FRAGMENT.getContext());
                break;
            default:
                break;

        }
    }

    private void getGenderDiglog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FRAGMENT.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
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
