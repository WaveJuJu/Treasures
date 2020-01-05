package com.treasures.cn.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.treasures.cn.R;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.utils
 * @ClassName: CarouselShow
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-22 23:07
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-22 23:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CarouselShow {
    private final Context context;
    private LinearLayout point_group;
    private List<ImageView> viewpage_imageList;
    protected int lastPosition = 0;

    public CarouselShow(Context context, List<ImageView> viewpage_imageList) {
        this.context = context;
        this.viewpage_imageList = viewpage_imageList;
    }

    /**
     * 当需要多个轮播功能的时候 建立一个类来调用 并实现此方法
     */
    public void CarouselShow_Info_Detail(ViewPager viewPager, LinearLayout point_detail) {
        // 设置当前viewPager的位置
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % viewpage_imageList.size()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int position) {
                // 页面切换后调用， position是新的页面位置
                // 实现无限制循环播放
                position %= viewpage_imageList.size();
                // 把当前点设置为true,将上一个点设为false；并设置point_group图标
                point_detail.getChildAt(lastPosition).setEnabled(false);
                point_detail.getChildAt(lastPosition).setBackgroundColor
                        (ContextCompat.getColor(viewPager.getContext(), R.color.white50));//上一张恢复原有图标
                point_detail.getChildAt(position).setEnabled(true);
                point_detail.getChildAt(position).setBackgroundColor
                        (ContextCompat.getColor(viewPager.getContext(), R.color.white));//设置聚焦时的图标样式

                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
