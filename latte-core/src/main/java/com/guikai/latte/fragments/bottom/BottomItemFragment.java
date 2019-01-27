package com.guikai.latte.fragments.bottom;

import android.widget.Toast;

import com.guikai.latte.R;
import com.guikai.latte.app.Latte;
import com.guikai.latte.fragments.LatteFragment;

/**
 * Created by Anding on 2019/1/27 14:29
 * Note:
 */

public abstract class BottomItemFragment extends LatteFragment{

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
