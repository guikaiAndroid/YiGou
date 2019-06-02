package com.guikai.latte.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guikai.latte.ui.R;
import com.guikai.latte.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        OnItemClickListener {

    private MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    //确保初始化一次Banner,防止重复Item加载
    private boolean mIsInitBanner = false;

    //设置图片加载策略
    private static final RequestOptions REQUEST_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        final String text;
        final String imageUrl;
        final View view;
        final ArrayList<String> bannerImages;
        final ArrayList<String> sectionIcons;
        final ArrayList<String> itemsImages;
        switch (holder.getItemViewType()) {
//            case ItemType.TEXT:
//                text = item.getField(MultipleFields.TEXT);
//                holder.setText(R.id.text_single, text);
//                break;
//            case ItemType.SECTION_IMAGE:
//                imageUrl = item.getField(MultipleFields.IMAGE_URL);
//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .apply(REQUEST_OPTIONS)
//                        .into((ImageView) holder.getView(R.id.img_section));
//                break;
//            case ItemType.TEXT_IMAGE:
//                text = item.getField(MultipleFields.TEXT);
//                imageUrl = item.getField(MultipleFields.IMAGE_URL);
//                holder.setText(R.id.tv_multiple, text);
//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .apply(REQUEST_OPTIONS)
//                        .into((ImageView) holder.getView(R.id.img_multiple));
//                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = item.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;
            case ItemType.INDEX_SECTION_FIVE:
                sectionIcons = item.getField(MultipleFields.SECTIONS);
                Glide.with(mContext).load(sectionIcons.get(0)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.i1));
                Glide.with(mContext).load(sectionIcons.get(1)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.i2));
                Glide.with(mContext).load(sectionIcons.get(2)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.i3));
                Glide.with(mContext).load(sectionIcons.get(3)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.i4));
                Glide.with(mContext).load(sectionIcons.get(4)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.i5));
                break;
            case ItemType.DIVIDER_LINE:
                break;
            case ItemType.INDEX_MAIN_THREE:
                itemsImages = item.getField(MultipleFields.IMAGE_ITEMS);
                Glide.with(mContext).load(itemsImages.get(0)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.img_left));
                Glide.with(mContext).load(itemsImages.get(1)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.img_top_right));
                Glide.with(mContext).load(itemsImages.get(2)).apply(REQUEST_OPTIONS).into((ImageView) holder.getView(R.id.img_bottom_right));
                break;
                case ItemType.IMAGE_AD:
                    imageUrl = item.getField(MultipleFields.IMAGE_URL);
                    Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_ad_single));
                break;
            case ItemType.SECTION_IMAGE:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_section));
                break;
//            case ItemType.IMAGE_DOUBLE:
//                imageUrl = item.getField(MultipleFields.IMAGE_URL);
//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .apply(REQUEST_OPTIONS)
//                        .into((ImageView) holder.getView(R.id.img_double));
//                break;
            default:
                break;
        }
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    private void init() {
        //设置不同的Item布局
        addItemType(ItemType.BANNER, R.layout.item_multipe_banner);
        addItemType(ItemType.INDEX_SECTION_FIVE, R.layout.item_multiple_five_image);
        addItemType(ItemType.SECTION_IMAGE, R.layout.item_multiple_single_image);
        addItemType(ItemType.DIVIDER_LINE, R.layout.item_multiple_divider_line);
        addItemType(ItemType.INDEX_MAIN_THREE, R.layout.item_multiple_three_image);
        addItemType(ItemType.IMAGE_AD, R.layout.item_multipe_ad_image);

        addItemType(ItemType.IMAGE_DOUBLE, R.layout.item_multipe_double_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);

//        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }


    @Override
    public void onItemClick(int position) {

    }

}
