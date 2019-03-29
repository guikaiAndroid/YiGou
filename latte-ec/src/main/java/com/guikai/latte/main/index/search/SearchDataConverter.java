package com.guikai.latte.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.guikai.latte.ui.recycler.DataConverter;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latte.util.storage.LattePreference;

import java.util.ArrayList;

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStl =
                LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStl.equals("")){
            final JSONArray array = JSONArray.parseArray(jsonStl);
            final int size = array.size();
            for (int i = 0; i<size;i++) {
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT,historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
