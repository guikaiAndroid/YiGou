package com.guikai.latte.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.ui.recycler.ItemType;
import com.guikai.latte.ui.recycler.MultipleFields;
import com.guikai.latte.ui.recycler.MultipleItemEntity;
import com.guikai.latteec.R;

import java.util.ArrayList;

public class ImageFragment extends LatteFragment {

    private RecyclerView mRecyclerView = null;

    private static final String ARG_PICTURES = "ARG_PICTURES";

    @Override
    public Object setLayout() {
        return R.layout.fragment_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mRecyclerView = $(R.id.rv_image_container);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }

    private void initImages() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            final ArrayList<String> pictures = arguments.getStringArrayList(ARG_PICTURES);
            final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
            final int size;
            if (pictures != null) {
                size = pictures.size();
                for (int i = 0; i < size; i++) {
                    final String imageUrl = pictures.get(i);
                    final MultipleItemEntity entity = MultipleItemEntity
                            .builder()
                            .setItemType(ItemType.SINGLE_BIG_IMAGE)
                            .setField(MultipleFields.IMAGE_URL, imageUrl)
                            .build();
                    entities.add(entity);
                }
                final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
                mRecyclerView.setAdapter(adapter);
            }
        }
    }

    public static ImageFragment create(ArrayList<String> pictures) {
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES, pictures);
        final ImageFragment delegate = new ImageFragment();
        delegate.setArguments(args);
        return delegate;
    }

}
