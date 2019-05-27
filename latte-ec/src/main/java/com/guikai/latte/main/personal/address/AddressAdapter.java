package com.guikai.latte.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.guikai.latte.net.RestClient;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latte.ui.recycler.MultipleViewHolder;
import com.guikai.latteec.R;

import java.util.List;

public class AddressAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder> {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (helper.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = item.getField(MultipleFields.NAME);
                final String phone = item.getField(AddressItemFields.PHONE);
                final String address = item.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = item.getField(MultipleFields.TAG);
                final int id = item.getField(MultipleFields.ID);

                final AppCompatTextView nameText = helper.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = helper.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = helper.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = helper.getView(R.id.tv_address_delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                .url("address.json")
                                .params("id", id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(helper.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
            default:
                break;

        }
    }


}
