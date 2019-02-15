package com.guikai.latte.main.sort.list;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.guikai.latte.main.sort.SortFragment;
import com.guikai.latte.ui.recycler.ItemType;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latte.ui.recycler.MultipleRecyclerAdapter;
import com.guikai.latte.ui.recycler.MultipleViewHolder;
import com.guikai.latteec.R;

import java.util.List;

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {


    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortFragment fragment) {
        super(data);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = item.getField(MultipleFields.TEXT);
                final boolean isClicked = item.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);

        }
    }
}
