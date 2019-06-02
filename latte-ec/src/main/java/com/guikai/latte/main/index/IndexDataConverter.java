package com.guikai.latte.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guikai.latte.ui.recycler.DataConverter;
import com.guikai.latte.ui.recycler.ItemType;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * 1轮播图  2商品宫格 3商品分类图 4商品详情图 5 小单图 6广告 7文字
 */
public class IndexDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int itemType = data.getInteger("type");
            final int id = data.getInteger("goodsId");
            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final JSONArray banners = data.getJSONArray("banners");
            final JSONArray sections = data.getJSONArray("sections");
            final JSONArray items = data.getJSONArray("items");
            final ArrayList<String> BannerImages = new ArrayList<>();
            final ArrayList<String> SectionIcons = new ArrayList<>();
            final ArrayList<String> ItemsImages = new ArrayList<>();
            int type = 0;
            switch (itemType) {
                case 1:
                    type = ItemType.TEXT;
                    break;
                case 2:
                    type = ItemType.IMAGE_AD;
                    break;
                case 3:
                    type = ItemType.SECTION_IMAGE;
                    break;
                case 4:
                    type = ItemType.TEXT_IMAGE;
                    break;
                case 5:
                    type = ItemType.BANNER;
                    //banner初始化
                    final int bannerSize = banners.size();
                    for (int j = 0; j < bannerSize; j++) {
                        final String banner = banners.getString(j);
                        BannerImages.add(banner);
                    }
                    break;
                case 6:
                    type = ItemType.INDEX_SECTION_FIVE;
                    final int sectionSize = sections.size();
                    for (int j = 0; j < sectionSize; j++) {
                        final String section = sections.getString(j);
                        SectionIcons.add(section);
                    }
                    break;
                case 7:
                    type = ItemType.INDEX_MAIN_THREE;
                    //banner初始化
                    final int ImageSize = items.size();
                    for (int j = 0; j < ImageSize; j++) {
                        final String ImageUrl = items.getString(j);
                        ItemsImages.add(ImageUrl);
                    }
                    break;
                case 8:
                    type = ItemType.IMAGE_DOUBLE;
                    break;
                case 9:
                    type = ItemType.DIVIDER_LINE;
                    break;
                default:
                    break;
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.BANNERS, BannerImages)
                    .setField(MultipleFields.SECTIONS,SectionIcons)
                    .setField(MultipleFields.IMAGE_ITEMS,ItemsImages)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }

//    @Override
//    public ArrayList<MultipleItemEntity> convert() {
//        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
//        final int size = dataArray.size();
//        for (int i = 0; i < size; i++) {
//            final JSONObject data = dataArray.getJSONObject(i);
//            final String imageUrl = data.getString("imageUrl");
//            final String text = data.getString("text");
//            final int spanSize = data.getInteger("spanSize");
//            final int id = data.getInteger("goodsId");
//            final JSONArray banners = data.getJSONArray("banners");
//
//            final ArrayList<String> bannerImages = new ArrayList<>();
//            int type = 0;
//            if (imageUrl == null && text != null) {
//                type = ItemType.TEXT;
//            } else if (imageUrl != null && text == null) {
//                type = ItemType.IMAGE;
//            } else if (imageUrl != null) {
//                type = ItemType.TEXT_IMAGE;
//            } else if (banners != null) {
//                type = ItemType.BANNER;
//                //banner初始化
//                final int bannerSize = banners.size();
//                for (int j = 0; j < bannerSize; j++) {
//                    final String banner = banners.getString(j);
//                    bannerImages.add(banner);
//                }
//            }
//
//            final MultipleItemEntity entity = MultipleItemEntity.builder()
//                    .setField(MultipleFields.ITEM_TYPE, type)
//                    .setField(MultipleFields.SPAN_SIZE, spanSize)
//                    .setField(MultipleFields.ID, id)
//                    .setField(MultipleFields.TEXT, text)
//                    .setField(MultipleFields.IMAGE_URL, imageUrl)
//                    .setField(MultipleFields.BANNERS, bannerImages)
//                    .build();
//
//            ENTITIES.add(entity);
//        }
//        return ENTITIES;
//    }
}
