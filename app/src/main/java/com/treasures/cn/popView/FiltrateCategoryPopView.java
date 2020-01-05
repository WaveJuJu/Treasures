package com.treasures.cn.popView;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.treasures.cn.R;
import com.treasures.cn.customView.CostomLinearLayoutManager;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.popView.adapter.FiltrateCategoryAdapter;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView
 * @ClassName: FiltrateCategoryPopView
 * @Description: java类作用描述 首页筛选类目下拉框
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-18 12:08
 */
public class FiltrateCategoryPopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;
    @BindView(R.id.category_type_pop_recycler)
    RecyclerView categoryTypeRecycler;
    FiltrateCategoryAdapter mCategoryAdapter;
    @BindView(R.id.back_linear)
    LinearLayout backLinear;
    private int maxHeight = 0;

    public FiltrateCategoryPopView(Activity activity, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        maxHeight = (int) mActivity.getResources().getDimension(R.dimen.dp_180);
        initView();
    }

    private void initView() {
        View layout = LayoutInflater.from(mActivity).inflate(R.layout.pop_window_filtrate_category_layout, null);
        ButterKnife.bind(this, layout);
        popView = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(mActivity, R.color.transparent));
        popView.setFocusable(true);
        popView.setBackgroundDrawable(dw);
        popView.setOutsideTouchable(false);

        popView.setWidth((int) mActivity.getResources().getDimension(R.dimen.dp_140));
        popView.setHeight(maxHeight);

        categoryTypeRecycler.setHasFixedSize(true);
        categoryTypeRecycler.setItemAnimator(new DefaultItemAnimator());
        categoryTypeRecycler.setLayoutManager(new CostomLinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mCategoryAdapter = new FiltrateCategoryAdapter(mActivity);
        categoryTypeRecycler.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setItemClickBlock((categoryType, position) -> {
            if (onFiltrateCategoryBlock != null) {
                onFiltrateCategoryBlock.onFiltrateClick(categoryType);
                close();
            }
        });
    }

    public interface OnFiltrateCategoryBlock {
        void onFiltrateClick(CategoryType categoryType);
    }

    private OnFiltrateCategoryBlock onFiltrateCategoryBlock;

    public void setOnFiltrateCategoryBlock(OnFiltrateCategoryBlock onFiltrateCategoryBlock) {
        this.onFiltrateCategoryBlock = onFiltrateCategoryBlock;
    }

    public void show(List<CategoryType> list, int selectedId) {
        backLinear.setBackgroundResource(R.mipmap.pop_left);
        initShowPop(list, selectedId);
    }

    /**
     * 编辑藏品时选择类目下拉框
     *
     * @param list
     * @param selectedId
     */
    public void modifyShowPop(List<CategoryType> list, int selectedId) {
        backLinear.setBackgroundResource(R.mipmap.pop_right);
        initShowPop(list, selectedId);
    }

    private void initShowPop(List<CategoryType> list, int selectedId) {
        View view = mActivity.findViewById(layoutId);
        view.post(() -> {
            float itemH = mActivity.getResources().getDimension(R.dimen.dp_40);
            int height = (int) (list.size() * itemH);
            if (height > maxHeight) {
                height = maxHeight;
            }
            popView.setHeight((int) (height + itemH));

            int[] location = new int[2];
            view.getLocationOnScreen(location);//一个控件在其整个屏幕上的坐标位置
            int x = location[0];//获取当前位置的横坐标
            int y = location[1];//获取当前位置的纵坐标
            if (popView != null && !popView.isShowing()) {
                popView.showAtLocation(view, Gravity.NO_GRAVITY, x, y + view.getHeight());
            }
            mCategoryAdapter.setList(list, selectedId);
            mCategoryAdapter.notifyDataSetChanged();
        });
    }

    public void close() {
        if (popView != null && popView.isShowing()) {
            popView.dismiss();
        }
    }
}
