package com.guikai.latte.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latte.ui.recycler.MultipleViewHolder;
import com.guikai.latteec.R;

import java.util.List;

public class SearchAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder> {

    public SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (helper.getItemViewType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = helper.getView(R.id.tv_search_item);
                final String history = item.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
