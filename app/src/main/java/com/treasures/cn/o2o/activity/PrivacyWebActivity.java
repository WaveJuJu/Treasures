package com.treasures.cn.o2o.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.treasures.cn.R;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.UserInfoHelp;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.treasures.cn.o2o
 * @ClassName: PrivacyWebActivity
 * @Description: 隐私权限
 */
public class PrivacyWebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.bottom_linear)
    LinearLayout bottom_linear;
    @BindView(R.id.line_view)
    View line_view;
    @BindView(R.id.title_rela)
    RelativeLayout title_rela;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_privacy_layout;
    }

    @OnClick({R.id.not_agreed_btn, R.id.agreed_btn,R.id.back_btn})
    public void backClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
            case R.id.not_agreed_btn:
                finish();
                System.exit(0);
                break;
            case R.id.agreed_btn:
                MemoryData.USER_INFO.setPrivacy(true);
                UserInfoHelp.saveUser();
                Intent intent = new Intent();
                intent.setClass(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        title_rela.setVisibility(MemoryData.USER_INFO.isPrivacy() ? View.VISIBLE : View.GONE);
        line_view.setVisibility(MemoryData.USER_INFO.isPrivacy() ? View.GONE : View.VISIBLE);
        bottom_linear.setVisibility(MemoryData.USER_INFO.isPrivacy() ? View.GONE : View.VISIBLE);
        WebSettings settings = web_view.getSettings();
        settings.setDomStorageEnabled(true);/*DOM存储API是否可用，默认false。*/
        settings.setAllowFileAccess(true);/*是否允许访问文件，默认允许。*/
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
        settings.setSupportZoom(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            settings.setLoadWithOverviewMode(true);//适应屏幕
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把所有内容放大webview等宽的一列中
        }
        settings.setAppCachePath(Constant.advertis_url);
        settings.setNeedInitialFocus(false);///当webview调用requestFocus时为webview设置节点
        settings.setJavaScriptCanOpenWindowsAutomatically(true);/*让JavaScript自动打开窗口，默认false。适用于JavaScript方法window.open()。*/
        settings.setAllowFileAccessFromFileURLs(true);/*是否允许运行在一个URL环境*/
        settings.setAllowUniversalAccessFromFileURLs(true);/*是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容*/
        web_view.post(() -> web_view.loadUrl(Constant.privacy_url));
        web_view.setWebViewClient(webViewClient);
    }

    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };
}
