package com.guikai.latte.ui.camera;

import android.net.Uri;

import com.guikai.latte.fragments.PermissionCheckFragment;
import com.guikai.latte.util.file.FileUtil;

/**
 * Created by Anding
 * 照相机调用类
 */
public class LatteCamera {

    public static Uri createCropFile() {
        //剪裁图片的地址
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckFragment delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
