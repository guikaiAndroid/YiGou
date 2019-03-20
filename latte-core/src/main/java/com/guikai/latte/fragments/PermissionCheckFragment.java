package com.guikai.latte.fragments;

import android.Manifest;

import com.guikai.latte.ui.camera.LatteCamera;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Anding on 2019/1/12 16:58
 * Note: 权限基类 给子类调用
 */


public abstract class PermissionCheckFragment extends BaseFragment {

//    //不是直接调用该方法
//    @NeedsPermission(Manifest.permission.CAMERA)
//    void startCamera() {
//        LatteCamera.start(this);
//    }
//
//    //这个是真正调用的方法
//    public void startCameraWithCheck() {}


}
