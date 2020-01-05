package com.treasures.cn.o2o.fragment;

import android.view.View;

import com.treasures.cn.R;
import com.treasures.cn.customView.CostomLinearLayoutManager;
import com.treasures.cn.entity.Category;
import com.treasures.cn.handler.CategoryHelp;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.o2o.BaseFragment;
import com.treasures.cn.o2o.adapter.CategoryAdapter;
import com.treasures.cn.o2o.adapter.CategoryTypesAdapter;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CategoryFragment extends BaseFragment {
    public static final String INTERFACE_NAME = CategoryFragment.class.getName() + "FUNCTION";

    @BindView(R.id.category_recycler)
    RecyclerView categoryRecycler;
    @BindView(R.id.category_type_recycler)
    RecyclerView categoryTypeRecycler;
    private CategoryAdapter mCategoryAdapter;
    private CategoryTypesAdapter mCategoryTypesAdapter;
    private int categoryIndex = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_category_layout;
    }

    @Override
    protected void initView() {
        List<Category> mCategoryArr = MemoryData.categories;

        if (mCategoryArr.size() <= 0) {
            categoryRecycler.setVisibility(View.GONE);
            return;
        }
        categoryRecycler.setVisibility(View.VISIBLE);
        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setItemAnimator(new DefaultItemAnimator());
        categoryRecycler.setLayoutManager(new CostomLinearLayoutManager(getMActivity(), LinearLayoutManager.VERTICAL, false));
        mCategoryAdapter = new CategoryAdapter(getContext(), mCategoryArr);
        categoryRecycler.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setSelectPosition(categoryIndex);
        mCategoryAdapter.setItemClickBlock((category, position) -> {
            categoryTypeRecycler.post(() -> {
                this.categoryIndex = position;
                this.mCategoryAdapter.setSelectPosition(position);
                mCategoryTypesAdapter.setData(category.getTypeArr());
                this.mCategoryTypesAdapter.notifyDataSetChanged();
            });
        });


        if (mCategoryArr.size() <= 0) {
            categoryTypeRecycler.setVisibility(View.GONE);
            return;
        }
        if (mCategoryArr.get(0).getTypeArr().size() <= 0) {
            categoryTypeRecycler.setVisibility(View.GONE);
            return;
        }
        categoryTypeRecycler.setVisibility(View.VISIBLE);
        categoryTypeRecycler.setHasFixedSize(true);
        categoryTypeRecycler.setItemAnimator(new DefaultItemAnimator());
        categoryTypeRecycler.setLayoutManager(new CostomLinearLayoutManager(getMActivity(), LinearLayoutManager.VERTICAL, false));
        mCategoryTypesAdapter = new CategoryTypesAdapter(getContext());
        categoryTypeRecycler.setAdapter(mCategoryTypesAdapter);
        mCategoryTypesAdapter.setData(mCategoryArr.get(0).getTypeArr());
        mCategoryTypesAdapter.setItemClickBlock((categoryType, isAdd, position) -> categoryTypeRecycler.post(() -> {
            categoryType.setCategoryId(mCategoryArr.get(categoryIndex).getId());
            if (isAdd) {
                CategoryHelp.addTypeToSetting(categoryType);
            } else {
                CategoryHelp.cancelTypeToSetting(categoryType);
            }
            mCategoryTypesAdapter.notifyItemChanged(position);
        }));
    }
}
