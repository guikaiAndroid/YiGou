package com.guikai.latte.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.blankj.utilcode.util.ToastUtils;
import com.guikai.latte.ui.camera.LatteCamera;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Anding on 2019/1/12 16:58
 * Note: 权限基类 给子类调用
 * 权限包括但不仅限于：手机拍照 读取手机数据
 */

@RuntimePermissions
public abstract class PermissionCheckFragment extends BaseFragment {

    //不是直接调用该方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        PermissionCheckFragmentPermissionsDispatcher.checkStoryPermissionWithPermissionCheck(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        PermissionCheckFragmentPermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied((Manifest.permission.CAMERA))
    void onCameraDenied() {
        ToastUtils.showShort("不允许拍照");
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        ToastUtils.showShort("永久拒绝权限");
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    //存储权限
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void checkStoryPermission() {
        LatteCamera.start(this);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStorageRationale(final PermissionRequest request) {
        showRationaleDialog(request);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onStorageDenied() {
        ToastUtils.showShort("存储权限已拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onStorageNever() {
        ToastUtils.showShort("存储权限已被永久拒绝");
    }

    //不是直接调用此方法
    private void showRationaleDialog(final PermissionRequest request) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
