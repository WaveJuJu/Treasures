package com.treasures.cn.o2o.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.treasures.cn.utils.ImageUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.adapter
 * @ClassName: PhotoPagerAdapter
 * @Description: java类作用描述 大图预览adapter
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-25 15:55
 */
public class PhotoPagerAdapter extends PagerAdapter {
    private List<String> imgList;
    private Context context;

    public PhotoPagerAdapter(Context context, List<String> imgList) {
        this.imgList = imgList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return imgList.size();
    }

    //指定复用的判断逻辑，固定写法：view == object
    @Override
    public boolean isViewFromObject(View view, Object object) {
        //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
        return view == object;
    }

    //返回要显示的条目内容
    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //container  容器  相当于用来存放imageView
        PhotoView photoView = new PhotoView(context);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Bitmap bitmap = ImageUtils.getScaleBitmap(context, imgList.get(position));
        photoView.setImageBitmap(bitmap);
        photoView.setOnClickListener(v -> {
            if (onItemClickBlock != null){
                onItemClickBlock.OnItemClick(position);
            }
        });
        photoView.setOnLongClickListener(v -> {
            if (onItemClickBlock != null){
                onItemClickBlock.OnLongItemClick(position);
            }
            return false;
        });
        //把图片添加到container中
        container.addView(photoView);
        //把图片返回给框架，用来缓存
        return photoView;
    }

    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        //object:刚才创建的对象，即要销毁的对象
        container.removeView((View) object);
    }

    public interface OnItemClickBlock {
        void OnItemClick(int position);
        void OnLongItemClick(int position);
    }
    private OnItemClickBlock onItemClickBlock;

    public void setOnItemClickBlock(OnItemClickBlock onItemClickBlock) {
        this.onItemClickBlock = onItemClickBlock;
    }
}
