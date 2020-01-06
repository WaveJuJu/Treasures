package com.treasures.cn.o2o.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.treasures.cn.R;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.handler.CategoryHelp;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.TreasuresHelp;
import com.treasures.cn.o2o.BaseFragment;
import com.treasures.cn.o2o.ClientApp;
import com.treasures.cn.popView.FiltrateCategoryPopView;
import com.treasures.cn.popView.PhotoSelectedPopView;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.DateTimeUtil;
import com.treasures.cn.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 修改添加藏品
 */
public class ModifyTreasuresFragment extends BaseFragment {
    private static final String TAG = ModifyTreasuresFragment.class.getName() + "FUNCTION";
    @BindView(R.id.modify_title_txt)
    TextView modifyTitleTxt;
    @BindViews({R.id.add_img1, R.id.add_img2, R.id.add_img3, R.id.add_img4, R.id.add_img5, R.id.add_img6
            , R.id.add_img7, R.id.add_img8, R.id.add_img9})
    List<RoundedImageView> addImages;
    @BindViews({R.id.delete_img1_btn, R.id.delete_img2_btn, R.id.delete_img3_btn, R.id.delete_img4_btn
            , R.id.delete_img5_btn, R.id.delete_img6_btn, R.id.delete_img7_btn, R.id.delete_img8_btn, R.id.delete_img9_btn})
    List<Button> deleteBtns;

    @BindView(R.id.addImgLinear)
    LinearLayout addImgLinear;

    private int clickImgTag = 0;
    private Treasures mTreasures;
    private String treasuresId;

    private Uri resultUri = null;
    private File mCutResultFile;  //裁剪图片存储路径

    @BindView(R.id.category_type_txt)
    TextView categoryTypeTxt;
    //    @BindView(R.id.share_btn)
//    ImageButton shareBtn;
    @BindView(R.id.modify_title_edit)
    EditText modifyTitleEdit;
    @BindView(R.id.modify_size_edit)
    EditText modifySizeEdit;
    @BindView(R.id.modify_year_edit)
    EditText modifyYearEdit;
    @BindView(R.id.modify_describe_edit)
    EditText modifyDescribeEdit;
    @BindView(R.id.modify_buy_time_edit)
    EditText modifyBuyTimeEdit;
    @BindView(R.id.modify_buy_price_edit)
    EditText modifyBuyPriceEdit;
    @BindView(R.id.modify_sell_price_edit)
    EditText modifySellPriceEdit;
    @BindView(R.id.modify_keywords_edit)
    EditText modifyKeywordsEdit;

    //    @BindView(R.id.is_selling_img)
//    ImageView isSellingImg;
//    @BindView(R.id.is_sold_img)
//    ImageView isSoldImg;
    @BindView(R.id.sale_rg)
    RadioGroup saleRg;
    @BindView(R.id.cant_sale_rb)
    RadioButton cantSaleRb;
    @BindView(R.id.can_sale_rb)
    RadioButton canSaleRb;
    @BindView(R.id.yet_sale_rb)
    RadioButton yetSaleRb;

    @BindView(R.id.modify_remark_edit)
    EditText modifyRemarkEdit;
    @BindView(R.id.modify_save_btn)
    Button modifySaveBtn;

    @BindView(R.id.keywordScrollView)
    HorizontalScrollView keywordScrollView;
    @BindView(R.id.keywordContentLinear)
    LinearLayout keywordContentLinear;
    private List<String> allKeywords = new ArrayList<>();
    private OnClickListener keywordsOnClick = ((v) -> modifyKeywordsEdit.post(() -> {
        int index = (int) v.getTag();
        if (allKeywords.size() > index) {
            String addKey = allKeywords.get(index);
            String text = modifyKeywordsEdit.getText().toString();
            if (TextUtils.isEmpty(text)) {
                text = allKeywords.get(index);
            } else if (!text.contains(addKey)) {
                text += "," + allKeywords.get(index);
            }
            modifyKeywordsEdit.setText(text);
        }
    }));

    @BindView(R.id.add_linear2)
    LinearLayout add_linear2;
    @BindView(R.id.add_linear3)
    LinearLayout add_linear3;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_modify_treasures_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getActivity().getIntent().getExtras();
        Object object = bundle.get(Constant.BundleKey.MODIFY_TREASURES);
        String type = bundle.getString(Constant.BundleKey.MODIFY_TYPE);
        if (TextUtils.equals(type, BusiConst.ModifyType.NEW_ADD.toString())) {
            modifyTitleTxt.post(() -> modifyTitleTxt.setText(getActivity().getString(R.string.add_treasures)));
            modifySaveBtn.post(() -> modifySaveBtn.setEnabled(false));
            initData();
        } else {
            modifyTitleTxt.post(() -> {
                if (TextUtils.equals(type, BusiConst.ModifyType.COPY.toString())) {
                    modifyTitleTxt.setText(getActivity().getString(R.string.add_treasures));
//                    shareBtn.setVisibility(View.GONE);
                } else {
                    modifyTitleTxt.setText(getActivity().getString(R.string.modify_treasures));
//                    shareBtn.setVisibility(View.VISIBLE);
                }
            });

            mTreasures = (Treasures) object;
            modifySaveBtn.post(() -> modifySaveBtn.setEnabled(true));
            reloadView();
        }
        modifyTitleEdit.post(() -> modifyTitleEdit.addTextChangedListener(new ClassOfTextWatcher(modifyTitleEdit)));
        modifySizeEdit.post(() -> modifySizeEdit.addTextChangedListener(new ClassOfTextWatcher(modifySizeEdit)));
        modifyYearEdit.post(() -> modifyYearEdit.addTextChangedListener(new ClassOfTextWatcher(modifyYearEdit)));
        modifyDescribeEdit.post(() -> modifyDescribeEdit.addTextChangedListener(new ClassOfTextWatcher(modifyDescribeEdit)));
        modifyBuyTimeEdit.post(() -> modifyBuyTimeEdit.addTextChangedListener(new ClassOfTextWatcher(modifyBuyTimeEdit)));
        modifyBuyPriceEdit.post(() -> modifyBuyPriceEdit.addTextChangedListener(new ClassOfTextWatcher(modifyBuyPriceEdit)));
        modifySellPriceEdit.post(() -> modifySellPriceEdit.addTextChangedListener(new ClassOfTextWatcher(modifySellPriceEdit)));
        modifyKeywordsEdit.post(() -> modifyKeywordsEdit.addTextChangedListener(new ClassOfTextWatcher(modifyKeywordsEdit)));
        modifyRemarkEdit.post(() -> modifyRemarkEdit.addTextChangedListener(new ClassOfTextWatcher(modifyRemarkEdit)));
        saleRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.cant_sale_rb:
                    mTreasures.setSoldType(-1);
                    break;
                case R.id.can_sale_rb:
                    mTreasures.setSoldType(0);
                    break;
                case R.id.yet_sale_rb:
                    mTreasures.setSoldType(1);
                    break;
            }
        });
        add_linear2.setVisibility(ClientApp.mInstance.isIs9Image() ? View.VISIBLE : View.GONE);
        add_linear3.setVisibility(ClientApp.mInstance.isIs9Image() ? View.VISIBLE : View.GONE);
        initKeywordsView();
    }

    /**
     * 修改藏品时，刷新界面，给对应view渲染数据
     */
    private void reloadView() {
        reloadImg();
        CategoryType categoryType = CategoryHelp.getCategoryById(mTreasures.getCategoryTypeId());
        if (categoryType != null) {
            categoryTypeTxt.post(() -> categoryTypeTxt.setText(categoryType.getName()));
        }
        modifyTitleEdit.post(() -> modifyTitleEdit.setText(mTreasures.getSubTitle()));
        modifySizeEdit.post(() -> modifySizeEdit.setText(String.valueOf(mTreasures.getSize())));
        modifyYearEdit.post(() -> modifyYearEdit.setText(mTreasures.getYear()));
        modifyDescribeEdit.post(() -> modifyDescribeEdit.setText(mTreasures.getDescribe()));
        modifyKeywordsEdit.post(() -> modifyKeywordsEdit.setText(TreasuresHelp.getKeywordsStr(mTreasures.getKeywordsArr())));
        modifyBuyTimeEdit.post(() -> modifyBuyTimeEdit.setText(mTreasures.getBuyTime()));
        modifyBuyPriceEdit.post(() -> modifyBuyPriceEdit.setText(String.valueOf(mTreasures.getBuyPrice())));
        modifySellPriceEdit.post(() -> modifySellPriceEdit.setText(String.valueOf(mTreasures.getSellingPrice())));
        modifyRemarkEdit.post(() -> modifyRemarkEdit.setText(mTreasures.getRemark()));
        saleRg.clearCheck();
        cantSaleRb.setChecked(mTreasures.getSoldType() == -1);
        canSaleRb.setChecked(mTreasures.getSoldType() == 0);
        yetSaleRb.setChecked(mTreasures.getSoldType() == 1);
    }

    private String getImgUrl(String name) {
        List<String> urls = mTreasures.getImages();
        for (String url : urls) {
            if (url.contains(name)) {
                return url;
            }
        }
        return "";
    }

    private void reloadImg() {
        addImgLinear.post(() -> {
            for (RoundedImageView imageView : addImages) {
                int tag = Integer.valueOf((String) imageView.getTag());
                String imageName = Constant.ImageId.imageNames[tag];
                String imageUrl = getImgUrl(imageName);
                if (!TextUtils.isEmpty(imageUrl)) {
                    Bitmap mBitmap = ImageUtils.getScaleBitmap(getContext(), imageUrl);
                    deleteBtns.get(tag).setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(mBitmap);
                } else {
                    deleteBtns.get(tag).setVisibility(View.GONE);
                    imageView.setImageResource(R.mipmap.add_img_back);
                }
            }
        });
    }

    /**
     * 初始化Treasures
     */
    private void initData() {
        Date date = new Date();
        treasuresId = String.valueOf(date.getTime());
        mTreasures = new Treasures();
        mTreasures.setCreateTime(DateTimeUtil.format(date));
        mTreasures.setId(treasuresId);
    }


    /**
     * 初始化 KeywordsView add 2 HorizontalScrollView
     */
    private void initKeywordsView() {
        TreasuresHelp.getAllTreasuresKeywordsType((success, error, data) -> {
            if (data != null && data.size() > 0) {
                allKeywords = data;
                keywordScrollView.setVisibility(View.VISIBLE);
                for (int i = 0; i < allKeywords.size(); i++) {
                    @SuppressLint("InflateParams") View view = LayoutInflater
                            .from(getActivity()).inflate(R.layout.keyword_item_text_layout, null);
                    TextView textView = view.findViewById(R.id.keywords_txt);
                    textView.setTag(i);
                    textView.setText(allKeywords.get(i));
                    textView.setOnClickListener(keywordsOnClick);
                    keywordContentLinear.addView(view);
                }
            } else {
                keywordScrollView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 其他点击事件
     *
     * @param v view
     */
    @OnClick({R.id.back_btn, R.id.category_type_txt, R.id.modify_save_btn})
    public void OnClick(View v) {
        if (v.getId() == R.id.modify_save_btn) {
            saveTreasures();
        } else if (v.getId() == R.id.category_type_txt) {
            selectedCategory(v);
        } else if (v.getId() == R.id.back_btn) {
            showHintManagePop();
        }
    }

    /**
     * 添加图片点击事件
     *
     * @param view view
     */
    @OnClick({R.id.add_img1, R.id.add_img2, R.id.add_img3, R.id.add_img4
            , R.id.add_img5, R.id.add_img6, R.id.add_img7, R.id.add_img8, R.id.add_img9
            , R.id.delete_img1_btn, R.id.delete_img2_btn, R.id.delete_img3_btn, R.id.delete_img4_btn
            , R.id.delete_img5_btn, R.id.delete_img6_btn, R.id.delete_img7_btn, R.id.delete_img8_btn
            , R.id.delete_img9_btn})
    void onAddImgClick(View view) {
        switch (view.getId()) {
            case R.id.add_img1:
            case R.id.add_img2:
            case R.id.add_img3:
            case R.id.add_img4:
            case R.id.add_img5:
            case R.id.add_img6:
            case R.id.add_img7:
            case R.id.add_img8:
            case R.id.add_img9:
                clickImgTag = Integer.valueOf((String) view.getTag());
                showPhotoSelectedPop();
                break;
            case R.id.delete_img1_btn:
            case R.id.delete_img2_btn:
            case R.id.delete_img3_btn:
            case R.id.delete_img4_btn:

            case R.id.delete_img5_btn:

            case R.id.delete_img6_btn:
            case R.id.delete_img7_btn:
            case R.id.delete_img8_btn:
            case R.id.delete_img9_btn:
                int tag = Integer.valueOf((String) view.getTag());
                deleteIcon(tag);
                break;
        }
    }

    private void deleteIcon(int index) {
        if (mTreasures.getImages().size() > index) {
            mTreasures.getImages().remove(index);
            reloadImg();
        }
    }

    /**
     * 显示确认删除弹窗
     */
    private void showPhotoSelectedPop() {
        PhotoSelectedPopView photoSelectedPopView = new PhotoSelectedPopView(mActivity, R.id.modify_main_linear);
        photoSelectedPopView.show();
        photoSelectedPopView.setClickListener(new PhotoSelectedPopView.OnPhotoClickListener() {
            @Override
            public void OnCameraClickListener() {
                initIcon(true, clickImgTag);
            }

            @Override
            public void OnPhotoClickListener() {
                initIcon(false, clickImgTag);
            }
        });
    }

    private void showHintManagePop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.confirm_close));
        builder.setPositiveButton(getMActivity().getString(R.string.confirm), (dialogInterface, i) -> getActivity().finish());
        builder.setNegativeButton(getMActivity().getString(R.string.cancel), (dialogInterface, i) -> {
        });
        builder.show();
    }


    /**
     * 初始化image path
     */
    private void initIcon(boolean isCamera, int tag) {
        String imageName = Constant.ImageId.imageNames[tag];
        if (TextUtils.isEmpty(imageName)) {
            Toast.makeText(getMActivity(), "没有可用图片name", Toast.LENGTH_SHORT).show();
            return;
        }
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mCutResultFile = new File(storageDir + File.separator + treasuresId + imageName);//DateTimeUtil.format(new Date(), "yyyyMMddHHmmss"));
        try {
            if (mCutResultFile.exists()) {
                mCutResultFile.delete();
            }
            mCutResultFile.createNewFile();
            if (isCamera) {
                openCamera();
            } else {
                openPhoto();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri 相册选取的img
     */
    private void photoClip(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            if (resultUri == null) {
                resultUri = Uri.fromFile(mCutResultFile);
            }
            // 调用系统中自带的图片剪裁
            String IMAGE_UNSPECIFIED = "image/*";
            intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);
//            if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
//                //裁剪框的比例
//                intent.putExtra("aspectX", 9998);
//                intent.putExtra("aspectY", 9999);
//            }else{
//                //裁剪框的比例
//                intent.putExtra("aspectX", 0.1);
//                intent.putExtra("aspectY", 0.1);
//            }
//            //输出图片大小
//            intent.putExtra("outputX", ScreenUtils.getWidth(getContext()));
//            intent.putExtra("outputY", ScreenUtils.getHeight(getContext()));
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            intent.putExtra("noFaceDetection", true);
            intent.putExtra("return-data", false);
            //设置裁剪照片完成后图片的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, resultUri);
            startActivityForResult(intent, Constant.RequestCode.SELECTED_PHOTO_CROP_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 200);
                return;
            }
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                return;
            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                return;
            }
        }
        // 打开系统拍照页面
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(getContext(), "com.treasures.cn.fileprovider", mCutResultFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCutResultFile));
        }
        startActivityForResult(intent, Constant.RequestCode.SELECTED_CAMERA_REQUEST_CODE);
    }

    /**
     * 打开相册
     */
    @SuppressLint("IntentReset")
    private void openPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 199);
                return;
            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 199);
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.RequestCode.SELECTED_PHOTO_REQUEST_CODE);
    }

    private void showPhoto(Bitmap bitmap, String path) {
        if (mTreasures.getImages().size() > clickImgTag) {
            mTreasures.getImages().set(clickImgTag, path);
        } else if (!mTreasures.getImages().contains(path)) {
            mTreasures.getImages().add(path);
        }
        reloadImg();
        resultUri = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            openCamera();
        } else if (requestCode == 199) {
            openPhoto();
        }
    }

    /**
     * 图片操作回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == Constant.RequestCode.SELECTED_PHOTO_REQUEST_CODE) {
                if (data == null || data.getData() == null) {
                    return;
                }
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                photoClip(uri);
            } else if (requestCode == Constant.RequestCode.SELECTED_CAMERA_REQUEST_CODE) {
                Uri uri = Uri.fromFile(mCutResultFile);
                if (uri == null) {
                    return;
                }
                photoClip(uri);
            } else if (requestCode == Constant.RequestCode.SELECTED_PHOTO_CROP_REQUEST_CODE) {
                Uri uri = resultUri;
                if (uri == null) {
                    return;
                }
                String filePath = uri.getPath();
                Bitmap bitmap = ImageUtils.getScaleBitmap(getContext(), filePath);
                showPhoto(bitmap, filePath);
            }
        }
    }

    /**
     * 选择分类界面
     */
    private void selectedCategory(View v) {
        List<CategoryType> allTypes = MemoryData.selectedCategoryTypes;
        if (allTypes.size() <= 0) {
            Toast.makeText(getActivity(),
                    getActivity().getString(R.string.please_add_your_treasures), Toast.LENGTH_SHORT).show();
            return;
        }
        FiltrateCategoryPopView filtrateCategoryPopView = new FiltrateCategoryPopView(getActivity(), v.getId());
        filtrateCategoryPopView.modifyShowPop(allTypes, mTreasures.getCategoryTypeId());
        filtrateCategoryPopView.setOnFiltrateCategoryBlock(categoryType -> {
            if (categoryType.getId() == mTreasures.getCategoryTypeId()) {
                mTreasures.setCategoryTypeId(0);
                categoryTypeTxt.setText(getActivity().getString(R.string.please_selected));
            } else {
                mTreasures.setCategoryTypeId(categoryType.getId());
                categoryTypeTxt.setText(categoryType.getName());
            }
            modifySaveBtn.setEnabled(isCanSave());
        });
    }

    /**
     * @description 保存Treasures
     * @author: WaveJuJu
     */
    private void saveTreasures() {
        String categoryStr = categoryTypeTxt.getText().toString();
        if (TextUtils.isEmpty(categoryStr)) {
            Toast.makeText(getActivity(), "请选择类目", Toast.LENGTH_SHORT).show();
            return;
        }
        String titleStr = modifyTitleEdit.getText().toString();
        if (TextUtils.isEmpty(titleStr)) {
            Toast.makeText(getActivity(), "名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String yearStr = modifyYearEdit.getText().toString();
        String describeSrt = modifyDescribeEdit.getText().toString();
        String keywordsStr = modifyKeywordsEdit.getText().toString();
        String size = modifySizeEdit.getText().toString();
        String buyTime = modifyBuyTimeEdit.getText().toString();
        String buyPriceStr = modifyBuyPriceEdit.getText().toString();
        double buyPrice = TextUtils.isEmpty(buyPriceStr) ? 0 : Double.parseDouble(buyPriceStr);
        String sellingPriceStr = modifySellPriceEdit.getText().toString();
        double sellingPrice = TextUtils.isEmpty(sellingPriceStr) ? 0 : Double.parseDouble(sellingPriceStr);
        String remark = modifyRemarkEdit.getText().toString();


        mTreasures.setSubTitle(titleStr);
        mTreasures.setYear(yearStr);
        mTreasures.setSize(size);
        mTreasures.setDescribe(describeSrt);
        if (!TextUtils.isEmpty(keywordsStr)) {
            mTreasures.setKeywordsArr(TreasuresHelp.getBreakKeywordArr(keywordsStr));
        }
        mTreasures.setBuyTime(buyTime);
        mTreasures.setBuyPrice(buyPrice);
        mTreasures.setSellingPrice(sellingPrice);
        mTreasures.setRemark(remark);
        try {
            TreasuresHelp.saveTreasuresCallback(mTreasures);
            getActivity().finish();
        } catch (BusiException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否可以保存
     *
     * @return boolean
     */
    private boolean isCanSave() {
        if (mTreasures.getCategoryTypeId() <= 0) {
            return false;
        }
        String categoryStr = categoryTypeTxt.getText().toString();
        if (TextUtils.isEmpty(categoryStr)) {
            return false;
        }
        String titleStr = modifyTitleEdit.getText().toString();
        return !TextUtils.isEmpty(titleStr);
    }

    private class ClassOfTextWatcher implements TextWatcher {
        ClassOfTextWatcher(View view) {
            TextView view1;
            if (view instanceof TextView)
                view1 = (TextView) view;
            else
                throw new ClassCastException(
                        "view must be an instance Of TextView");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged: " + s + ", " + start);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged: " + s);
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged: " + s);
            modifySaveBtn.setEnabled(isCanSave());
        }
    }
}
