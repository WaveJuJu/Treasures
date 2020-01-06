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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.treasures.cn.R;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.UserInfoHelp;
import com.treasures.cn.o2o.BaseFragment;
import com.treasures.cn.o2o.ClientApp;
import com.treasures.cn.o2o.activity.DataStatisticsActivity;
import com.treasures.cn.o2o.activity.InviteCodeActivity;
import com.treasures.cn.o2o.activity.login.RegisteredActivity;
import com.treasures.cn.popView.PhotoSelectedPopView;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.ImageUtils;
import com.treasures.cn.utils.InviteCodeUtils;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

public class UserFragment extends BaseFragment {
    public static final String INTERFACE_NAME = UserFragment.class.getName() + "FUNCTION";

    @BindView(R.id.user_name_txt)
    TextView userNameTxt;
    @BindView(R.id.add_password_txt)
    TextView addPasswordTxt;
    @BindView(R.id.invite_code_hint_txt)
    TextView invite_code_hint_txt;
    @BindView(R.id.user_hear_img)
    RoundedImageView userHearImg;
    @BindView(R.id.version_txt)
    TextView version_txt;
    private Uri resultUri = null;
    private File mCutResultFile;  //裁剪图片存储路径

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_layout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();
        userNameTxt.post(() -> {
            userNameTxt.setText(TextUtils.isEmpty(MemoryData.USER_INFO.getNiceName())
                    ? getActivity().getString(R.string.app_name) : MemoryData.USER_INFO.getNiceName());
            addPasswordTxt.setText(TextUtils.isEmpty(MemoryData.USER_INFO.getPassword())
                    ? getActivity().getString(R.string.add_password) : getActivity().getString(R.string.modify_password));
        });
        userHearImg.post(() -> {
            if (!TextUtils.isEmpty(MemoryData.USER_INFO.getHeaderImgPath())) {
                Bitmap mBitmap = ImageUtils.getScaleBitmap(getContext(), MemoryData.USER_INFO.getHeaderImgPath());
                userHearImg.setImageBitmap(mBitmap);
            } else {
                userHearImg.setImageResource(R.mipmap.logo_icon);
            }
        });

        invite_code_hint_txt.post(() -> {
            String inviteCode = MemoryData.USER_INFO.getInviteCode();
            if (TextUtils.isEmpty(inviteCode) || UserInfoHelp.isSuccessInvice()) {
                invite_code_hint_txt.setVisibility(View.GONE);
            } else {
                if (InviteCodeUtils.isInviteCodeValid(true, inviteCode)) {
                    invite_code_hint_txt.setVisibility(View.VISIBLE);
                    invite_code_hint_txt.setText(getMActivity().getString(R.string.invitation_hint));
                } else {
                    invite_code_hint_txt.setVisibility(View.GONE);
                }
            }
        });

        version_txt.post(()-> version_txt.setText(ClientApp.getVersion()+"("+ClientApp.getBuildVersion()+")"));
    }

    @OnClick({R.id.add_password_rela, R.id.user_hear_img
            , R.id.my_collection_rela, R.id.delete_rela
            , R.id.user_name_txt, R.id.invite_code_rela
            , R.id.data_rela})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_password_rela:
                addPasswordTxt.post(() -> {
                    RegisteredActivity.openRegisteredActivity(mActivity, BusiConst.RegisteredStatus.MODIFY);
                });
                break;
            case R.id.user_hear_img:
                showPhotoSelectedPop();
                break;
            case R.id.my_collection_rela:
                if (lastClick()){
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constant.BundleKey.DATA_TYPE, BusiConst.RecycleStatus.RECYCLE.toString());
                    Navigation.findNavController(v).navigate(R.id.action_navigation_user_to_myCollectionActivity, bundle1);
                }
                break;
            case R.id.delete_rela:
                if (lastClick()){
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(Constant.BundleKey.DATA_TYPE, BusiConst.RecycleStatus.DELETE.toString());
                    Navigation.findNavController(v).navigate(R.id.action_navigation_user_to_myCollectionActivity, bundle2);
                }
                break;
            case R.id.user_name_txt:
                showEditNamePop();
                break;
            case R.id.invite_code_rela:
                InviteCodeActivity.openTreasureDetailActivity(getMActivity());
                break;
            case R.id.data_rela:
                DataStatisticsActivity.openDataStatisticsActivity(getMActivity());
                break;
        }
    }

    private void showEditNamePop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.logo_icon);
        builder.setTitle(getMActivity().getString(R.string.please_input_user_name));
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getMActivity()).inflate(R.layout.edit_user_name_dialog_layout, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);

        EditText username = view.findViewById(R.id.userNameEdit);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String name = username.getText().toString().trim();
            MemoryData.USER_INFO.setNiceName(name);
            UserInfoHelp.saveUser();
            initView();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();

    }

    /**
     * 显示确认删除弹窗
     */
    private void showPhotoSelectedPop() {
        PhotoSelectedPopView photoSelectedPopView = new PhotoSelectedPopView(mActivity, R.id.main_rela);
        photoSelectedPopView.show();
        photoSelectedPopView.setClickListener(new PhotoSelectedPopView.OnPhotoClickListener() {
            @Override
            public void OnCameraClickListener() {
                initIcon(false);
            }

            @Override
            public void OnPhotoClickListener() {
                initIcon(true);
            }
        });
    }

    /**
     * 初始化image path
     */
    private void initIcon(boolean isPhoto) {
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mCutResultFile = new File(storageDir + File.separator + Constant.ImageId.USER_HEAD_NAME);
        try {
            if (mCutResultFile.exists()) {
                mCutResultFile.delete();
            }
            mCutResultFile.createNewFile();
            if (isPhoto) {
                openPhoto();
            } else {
                openCamera();
            }
        } catch (IOException e) {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCutResultFile));
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
            //裁剪框的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //输出图片大小
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 400);
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
                this.photoClip(uri);
            } else if (requestCode == Constant.RequestCode.SELECTED_CAMERA_REQUEST_CODE) {
                Uri uri = Uri.fromFile(mCutResultFile);
                if (uri == null) {
                    return;
                }
                this.photoClip(uri);
            } else if (requestCode == Constant.RequestCode.SELECTED_PHOTO_CROP_REQUEST_CODE) {
                if (resultUri == null) {
                    return;
                }
                userHearImg.post(() -> {
                    if (!TextUtils.isEmpty(resultUri.getPath())) {
                        MemoryData.USER_INFO.setHeaderImgPath(resultUri.getPath());
                        UserInfoHelp.saveUser();
                        initView();
                        resultUri = null;
                    }
                });
            }
        }
    }
}
