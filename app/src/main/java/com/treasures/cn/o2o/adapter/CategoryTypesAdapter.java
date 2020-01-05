package com.treasures.cn.o2o.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treasures.cn.R;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.handler.CategoryHelp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryTypesAdapter extends RecyclerView.Adapter<CategoryTypesAdapter.ViewHolder> {
    private List<CategoryType> mList;
    private Context mContext;

    private OnItemClickBlock itemClickBlock;

    public void setItemClickBlock(OnItemClickBlock itemClickBlock) {
        this.itemClickBlock = itemClickBlock;
    }

    public CategoryTypesAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CategoryType> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gategory_type_layout, parent, false);
        return new CategoryTypesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryType categoryType = mList.get(position);
        holder.typeNameTxt.setText(categoryType.getName());
        boolean isAdd = CategoryHelp.isSelectedCategoryTypes(categoryType) < 0;
        holder.addTxt.setText(mContext.getString(isAdd ? R.string.add : R.string.cancel));
        holder.addTxt.setTextColor(ContextCompat.getColor(mContext, isAdd ? R.color.main_color : R.color.gray_b2));
        holder.addTxt.setBackground(ContextCompat.getDrawable(mContext, isAdd ? R.drawable.home_category_add_button_back
                : R.drawable.home_category_cancel_button_back));
        holder.addTxt.setOnClickListener(v -> {
            if (itemClickBlock != null) {
                itemClickBlock.OnItemClickBlock(categoryType, isAdd, position);
            }
        });

        holder.typeCountTxt.post(() -> {
            int count = CategoryHelp.getCategotyTypeHaveTreasuresCount(categoryType.getId());
            holder.typeCountTxt.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
            holder.typeCountTxt.setText(String.valueOf(count));
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_name_txt)
        TextView typeNameTxt;
        @BindView(R.id.add_txt)
        TextView addTxt;
        @BindView(R.id.type_count_txt)
        TextView typeCountTxt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnItemClickBlock {
        void OnItemClickBlock(CategoryType category, boolean isAdd, int position);
    }
}
