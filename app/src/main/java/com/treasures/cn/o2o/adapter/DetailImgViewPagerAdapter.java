package com.treasures.cn.o2o.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.adapter
 * @ClassName: DetailImgViewPagerAdapter
 * @Author: WaveJuJu
 */
public class DetailImgViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews = new ArrayList<>();

    public DetailImgViewPagerAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();//Integer.MAX_VALUE;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position % imageViews.size());
        imageView.setOnClickListener(v -> {
            if (onItemClickBlock != null) {
                onItemClickBlock.OnItemClick(position);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        object = null;
    }

    public interface OnItemClickBlock {
        void OnItemClick(int position);
    }

    private OnItemClickBlock onItemClickBlock;

    public void setOnItemClickBlock(OnItemClickBlock onItemClickBlock) {
        this.onItemClickBlock = onItemClickBlock;
    }
}
