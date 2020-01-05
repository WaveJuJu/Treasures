package com.treasures.cn.o2o.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.o2o.adapter.DetailImgViewPagerAdapter;
import com.treasures.cn.popView.ShareTreasurePopView;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.CarouselShow;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.ImageUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.activity.login
 * @ClassName: TreasureDetailActivity
 * @Description: java类作用描述 藏品详情界面
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-22 22:18
 */
public class TreasureDetailActivity extends BaseActivity {
    private Treasures treasures;
    private List<ImageView> viewPageImgArr;
    public static void openTreasureDetailActivity(Activity activity, Treasures treasures) {
        Intent intent = new Intent();
        intent.setClass(activity, TreasureDetailActivity.class);
        intent.putExtra(Constant.BundleKey.TREAURES_DETAIL, treasures);
        activity.startActivity(intent);
    }

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.point_detail_linear)
    LinearLayout pointDetailLinear;
    @BindView(R.id.page_rela)
    RelativeLayout pageRela;
    @BindView(R.id.notImgTxt)
    TextView notImgTxt;

    @BindView(R.id.detail_name_txt)
    TextView detail_name_txt;
    @BindView(R.id.detail_desc_txt)
    TextView detail_desc_txt;
    @BindView(R.id.detail_size_txt)
    TextView detail_size_txt;
    @BindView(R.id.detail_year_txt)
    TextView detail_year_txt;
    @BindView(R.id.detail_buy_time_txt)
    TextView detail_buy_time_txt;
    @BindView(R.id.detail_buy_price_txt)
    TextView detail_buy_price_txt;
    @BindView(R.id.detail_sell_price_txt)
    TextView detail_sell_price_txt;
    @BindView(R.id.detail_sale_status_txt)
    TextView detail_sale_status_txt;
    //    @BindView(R.id.detail_keyword_scroll)
//    HorizontalScrollView detail_keyword_scroll;
//    @BindView(R.id.detail_keyword_linear)
//    LinearLayout detail_keyword_linear;
    @BindView(R.id.detail_remark_txt)
    TextView detail_remark_txt;

    //    @BindView(R.id.detail_keyword_line)
//    View detail_keyword_line;
//    @BindView(R.id.detail_keyword_include)
//    View detail_keyword_include;
    @BindView(R.id.detail_remark_line)
    View detail_remark_line;
    @BindView(R.id.detail_remark_inclue)
    View detail_remark_inclue;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_treasure_detail_layout;
    }


    @Override
    protected void initView() {
        super.initView();
        treasures = (Treasures) getIntent().getSerializableExtra(Constant.BundleKey.TREAURES_DETAIL);
        if (treasures == null) {
            this.finish();
            return;
        }
        reloadView();
        if (treasures.getImages().size() <= 0) {
            viewPager.setVisibility(View.GONE);
            pointDetailLinear.setVisibility(View.GONE);
            notImgTxt.setVisibility(View.VISIBLE);
            return;
        }
        viewPager.post(this::initViewPager);
    }

    @SuppressLint("SetTextI18n")
    private void reloadView() {
        detail_name_txt.setText(treasures.getSubTitle());

        String desc = treasures.getDescribe();
        if (desc == null || TextUtils.isEmpty(desc)) {
            detail_desc_txt.setVisibility(View.GONE);
        } else {
            detail_desc_txt.setVisibility(View.VISIBLE);
            detail_desc_txt.setText(desc);
        }
        String size = treasures.getSize();
        if (size == null || TextUtils.isEmpty(size)) {
            size = " -- ";
        }
        detail_size_txt.setText(getMActivity().getString(R.string.detail_size_title) + size);

        String year = treasures.getYear();
        if (year == null || TextUtils.isEmpty(year)) {
            year = " -- ";
        }
        detail_year_txt.setText(getMActivity().getString(R.string.detail_year_title) + year);

        String buyTime = treasures.getBuyTime();
        if (buyTime == null || TextUtils.isEmpty(buyTime)) {
            buyTime = " -- ";
        }
        detail_buy_time_txt.setText(getMActivity().getString(R.string.detail_buy_time) + buyTime);

        double buyPrice = treasures.getBuyPrice();
        String buyPriceStr = " -- ";
        if (buyPrice > 0) {
            buyPriceStr = String.valueOf(buyPrice);
        }
        detail_buy_price_txt.setText(getMActivity().getString(R.string.detail_buy_price) + buyPriceStr);

        double sellPrice = treasures.getSellingPrice();
        String sellPriceStr = " -- ";
        if (sellPrice > 0) {
            sellPriceStr = String.valueOf(sellPrice);
        }
        detail_sell_price_txt.setText(getMActivity().getString(R.string.detail_sell_price) + sellPriceStr);

        String saleStatu = getTreasuresStatus(treasures.getSoldType());
        if (saleStatu == null || TextUtils.isEmpty(saleStatu)) {
            saleStatu = " -- ";
        }
        detail_sale_status_txt.setText(getMActivity().getString(R.string.detail_sale_status) + saleStatu);

//        if (treasures.getKeywordsArr() != null && treasures.getKeywordsArr().size() > 0) {
//            detail_keyword_line.setVisibility(View.VISIBLE);
//            detail_keyword_include.setVisibility(View.VISIBLE);
//            for (int i = 0; i < treasures.getKeywordsArr().size(); i++) {
//                @SuppressLint("InflateParams") View view = LayoutInflater
//                        .from(getMActivity()).inflate(R.layout.keyword_item_text_layout, null);
//                TextView textView = view.findViewById(R.id.keywords_txt);
//                textView.setTag(i);
//                textView.setText(treasures.getKeywordsArr().get(i));
//                detail_keyword_linear.addView(view);
//            }
//        } else {
//            detail_keyword_line.setVisibility(View.GONE);
//            detail_keyword_include.setVisibility(View.GONE);
//        }

        String remark = treasures.getRemark();
        if (remark == null || TextUtils.isEmpty(remark)) {
            detail_remark_inclue.setVisibility(View.GONE);
            detail_remark_line.setVisibility(View.GONE);
        } else {
            detail_remark_txt.setText(remark);
            detail_remark_inclue.setVisibility(View.VISIBLE);
            detail_remark_line.setVisibility(View.VISIBLE);
        }


    }

    private String getTreasuresStatus(int soldType) {
        if (soldType == -1) {//是否可售 -1不可售 0可售 1已售
            return getMActivity().getString(R.string.cant_sale);
        } else if (soldType == 0) {
            return getMActivity().getString(R.string.can_sale);
        } else if (soldType == 1) {
            return getMActivity().getString(R.string.yet_sale);
        }
        return " -- ";
    }

    @SuppressLint("ResourceAsColor")
    private void initViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        pointDetailLinear.setVisibility(View.VISIBLE);
        notImgTxt.setVisibility(View.GONE);
        viewPageImgArr = new ArrayList<>();
        int pointW = (int) mActivity.getResources().getDimension(R.dimen.dp_18);
        int pointH = (int) mActivity.getResources().getDimension(R.dimen.dp_4);
        pointDetailLinear.post(() -> {
            for (int i = 0; i < treasures.getImages().size(); i++) {
                String imageUrl = treasures.getImages().get(i);
                ImageView imageView = new ImageView(getMActivity());
                Bitmap mBitmap = ImageUtils.getScaleBitmap(getMActivity(), imageUrl);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setBackgroundColor(R.color.black);
                imageView.setImageBitmap(mBitmap);
                viewPageImgArr.add(imageView);
                // 添加指示小点
                View pointImg = new View(this);
                pointImg.setBackgroundColor(ContextCompat.getColor(getMActivity(), R.color.white50));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointW,
                        pointH);
                params.rightMargin = (int) getResources().getDimension(R.dimen.dp_3);
                params.leftMargin = (int) getResources().getDimension(R.dimen.dp_3);
                pointImg.setLayoutParams(params);
                if (i == 0) {
                    //默认聚焦在第一张
                    pointImg.setBackgroundColor(ContextCompat.getColor(getMActivity(), R.color.white));
                    pointImg.setEnabled(true);
                } else {
                    pointImg.setEnabled(false);
                }
                pointDetailLinear.addView(pointImg);
            }
            //首页轮播
            CarouselShow carouselShow = new CarouselShow(getMActivity(), viewPageImgArr);
            DetailImgViewPagerAdapter adapter = new DetailImgViewPagerAdapter(viewPageImgArr);
            adapter.setOnItemClickBlock(position -> {
                PhotoPageActivity.openPhotoPageActivity(getMActivity(), treasures, position);
            });
            viewPager.setAdapter(adapter);
            carouselShow.CarouselShow_Info_Detail(viewPager, pointDetailLinear);
            viewPager.setCurrentItem(0);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back_btn, R.id.share_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.share_btn:
                shareTreasure();
                break;
        }
    }

    private void shareTreasure() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE
                    , Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP
                    , Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS
                    , Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        } else {
            showSharePop();
        }
    }

    /**
     * 添加完权限后回来
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            showSharePop();
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


    /**
     * 显示操作弹窗
     */
    private void showSharePop() {
        ShareTreasurePopView shareTreasurePopView = new ShareTreasurePopView(mActivity, treasures, R.id.detail_main_linear);
        shareTreasurePopView.show();
        shareTreasurePopView.setOnShareTreasureClick((type, bitmap) -> startShareTreasures(bitmap, type));
    }
}
