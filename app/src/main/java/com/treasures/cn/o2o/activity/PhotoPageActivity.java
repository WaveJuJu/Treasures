package com.treasures.cn.o2o.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.o2o.adapter.PhotoPagerAdapter;
import com.treasures.cn.popView.ShareTreasurePopView;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.ImageUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.activity
 * @ClassName: PhotoPageActivity
 * @Description: 图片预览
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-25 15:32
 */
public class PhotoPageActivity extends BaseActivity {
    public static void openPhotoPageActivity(Activity activity, Treasures treasures, int position) {
        Intent intent = new Intent(activity, PhotoPageActivity.class);
        intent.putExtra(Constant.BundleKey.PHOTO_TREASURE, treasures);
        intent.putExtra(Constant.BundleKey.PHOTO_POSITION, position);
        activity.startActivity(intent);
    }

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.pointsLinear)
    LinearLayout pointsLinear;
    private int prePosition = 0;
    PhotoPagerAdapter mPagerAdapter;
    Treasures mTreasure;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_photo_page_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        mTreasure = (Treasures) getIntent().getSerializableExtra(Constant.BundleKey.PHOTO_TREASURE);
        prePosition = getIntent().getIntExtra(Constant.BundleKey.PHOTO_POSITION, 0);

        if (mTreasure == null || mTreasure.getImages().size() <= 0) {
            finish();
        }
        mPagerAdapter = new PhotoPagerAdapter(getMActivity().getApplicationContext(), mTreasure.getImages());
        mPagerAdapter.setOnItemClickBlock(new PhotoPagerAdapter.OnItemClickBlock() {
            @Override
            public void OnItemClick(int position) {
                getMActivity().finish();
            }

            @Override
            public void OnLongItemClick(int position) {
                showShareImagePop(position);
            }
        });
        viewPager.setAdapter(mPagerAdapter);
        for (int i = 0; i < mTreasure.getImages().size(); i++) {
            //白点
            //根据viewPager的数量，添加白点指示器
            ImageView view = new ImageView(this);
            view.setBackgroundResource(R.drawable.point_back);
            //给点设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            //给控件设置边距
            params.leftMargin = 10;
            //给view设置参数
            view.setLayoutParams(params);
            //将图片添加到线性布局中
            pointsLinear.addView(view);
        }

        pointsLinear.getChildAt(prePosition).setBackgroundResource(R.drawable.point_white);
        viewPager.setCurrentItem(prePosition);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
                position = position % mTreasure.getImages().size();
                //把前一个白变为黑
                pointsLinear.getChildAt(prePosition).setBackgroundResource(R.drawable.point_back);
                //把当前白点变为黑点
                pointsLinear.getChildAt(position).setBackgroundResource(R.drawable.point_white);
                //记录下当前位置(当前位置变白后，赋值给前一个点)
                prePosition = position;
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void showShareImagePop(int position){
        if (mTreasure.getImages().size() > position){
            Bitmap bitmapImg = ImageUtils.getScaleBitmap(getMActivity().getApplicationContext(), mTreasure.getImages().get(position));
            ShareTreasurePopView shareTreasurePopView = new ShareTreasurePopView(mActivity, bitmapImg, R.id.photo_main_rela);
            shareTreasurePopView.showShareImg();
            shareTreasurePopView.setOnShareTreasureClick((type, bitmap) -> startShareTreasures(bitmap, type));
        }
    }

    private void startShareTreasures(Bitmap bitmap, BusiConst.ShareType shareType) {
        UMImage image = new UMImage(getMActivity(), bitmap);//bitmap文件
        image.compressStyle = UMImage.CompressStyle.QUALITY;
        new ShareAction(getMActivity())
                .setPlatform(TextUtils.equals(shareType.toString()
                        , BusiConst.ShareType.WECHAT_FRIENDS.toString())
                        ? SHARE_MEDIA.WEIXIN : SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        // 分享开始的回调
        // platform 平台类型
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        /**
         * @descrption 分享成功的回调
         */
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(mActivity, getMActivity().getString(R.string.share_success), Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param share_media 平台类型
         * @param throwable 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(mActivity, getMActivity().getString(R.string.share_error), Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param share_media 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mActivity, getMActivity().getString(R.string.cancel_share), Toast.LENGTH_SHORT).show();
        }
    };
}
