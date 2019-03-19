package com.guikai.latte.main.personal.profile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.main.personal.list.ListBean;
import com.guikai.latte.ui.data.DateDialogUtil;
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
                //拍照
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
                        final TextView text = view.findViewById(R.id.tv_arrow_text);
                        text.setText("生日");
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
