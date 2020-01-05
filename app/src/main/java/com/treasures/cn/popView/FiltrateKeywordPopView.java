package com.treasures.cn.popView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.treasures.cn.R;
import com.treasures.cn.popView.adapter.FiltrateKeywordAdapter;
import com.treasures.cn.utils.ScreenUtils;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView
 * @ClassName: FiltrateKeywordPopView
 * @Description: java类作用描述 筛选关键词弹窗
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-25 11:49
 */
public class FiltrateKeywordPopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;

    @BindView(R.id.keyword_recycler_view)
    RecyclerView keyword_recycler_view;
    FiltrateKeywordAdapter adapter;
    private String selectedKey = "";
    @BindView(R.id.content_linear)
    LinearLayout content_linear;

    float maxHeight = 0;

    public FiltrateKeywordPopView(Activity activity, String selectedKey, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        this.selectedKey = selectedKey;
        this.maxHeight = mActivity.getResources().getDimension(R.dimen.dp_336);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.pop_window_filtrate_keyword_layout, null);
        ButterKnife.bind(this, layout);
        popView = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popView.setFocusable(true);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.setOutsideTouchable(true);//点击外面不关闭

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4);
        keyword_recycler_view.setLayoutManager(gridLayoutManager);
        keyword_recycler_view.setHasFixedSize(true);
        keyword_recycler_view.setItemAnimator(new DefaultItemAnimator());
        adapter = new FiltrateKeywordAdapter(mActivity.getApplicationContext());
        keyword_recycler_view.setAdapter(adapter);
        adapter.setOnItemClickBlock((keyword) -> {
            if (TextUtils.equals(selectedKey, keyword)){
                selectedKey = "";
            }else{
                selectedKey = keyword;
            }
            adapter.setSelectPosition(selectedKey);
        });
    }

    @OnClick({R.id.filtrate_pop_confirm_btn, R.id.filtrate_pop_cancel_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filtrate_pop_confirm_btn:
                if (onItemClickBlock != null) {
                    onItemClickBlock.OnItemClickBlock(selectedKey);
                    close();
                }
                break;
            case R.id.filtrate_pop_cancel_btn:
                close();
                break;
        }
    }

    public void show(List<String> data) {
        if (data == null || data.size() <= 0) {
            close();
            return;
        }
        View view = mActivity.findViewById(layoutId);
        view.post(() -> {
            float itemH = mActivity.getResources().getDimension(R.dimen.dp_57);

            int[] location = new int[2];
            view.getLocationOnScreen(location);//一个控件在其整个屏幕上的坐标位置
            int x = location[0];//获取当前位置的横坐标
            int y = location[1];//获取当前位置的纵坐标
            int popY = y + view.getHeight();
            content_linear.post(() -> {
                int contentH = (int) (itemH * data.size());
                if (contentH > maxHeight) {
                    contentH = (int) maxHeight;
                }
                content_linear.getLayoutParams().height = contentH;
            });

            popView.setHeight(ScreenUtils.getHeight(mActivity.getApplicationContext()) - popY);
            if (popView != null && !popView.isShowing()) {
                popView.showAtLocation(view, Gravity.NO_GRAVITY, x, popY);
            }
            adapter.setList(data, selectedKey);
            adapter.notifyDataSetChanged();
        });
    }

    private void close() {
        if (popView != null && popView.isShowing()) {
            if (onItemClickBlock != null) {
                onItemClickBlock.OnDismissListener();
            }
            popView.dismiss();
        }
    }

    private OnItemClickBlock onItemClickBlock;

    public void setOnItemClickBlock(OnItemClickBlock onItemClickBlock) {
        this.onItemClickBlock = onItemClickBlock;
    }

    public interface OnItemClickBlock {
        void OnItemClickBlock(String keyword);
        void OnDismissListener();
    }
}
