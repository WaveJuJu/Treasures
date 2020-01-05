package com.treasures.cn.o2o.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.treasures.cn.R;
import com.treasures.cn.entity.Category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> mList;
    private Context mContext;
    private int selectPosition = 0;
    private OnItemClickBlock itemClickBlock;

    public void setItemClickBlock(OnItemClickBlock itemClickBlock) {
        this.itemClickBlock = itemClickBlock;
    }

    public CategoryAdapter(Context context, List<Category> categories) {
        this.mContext = context;
        this.mList = categories;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemRangeChanged(0, mList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gategory_layout, parent, false);
        return new CategoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boolean isSelected = position == selectPosition;
        Category category = mList.get(position);
        holder.selectedLineView.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        holder.categoryNameTxt.setText(category.getName());
        holder.categoryNameTxt.setTextColor(ContextCompat.getColor(mContext, isSelected ? R.color.title_clolor: R.color.gray_b2));
        holder.categotyRela.setBackgroundColor(ContextCompat.getColor(mContext, isSelected ? R.color.white : R.color.gray_f4));
        holder.categotyRela.setOnClickListener(v -> {
            if (itemClickBlock != null) {
                itemClickBlock.OnItemClickBlock(category,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoty_rela)
        RelativeLayout categotyRela;
        @BindView(R.id.selected_line_view)
        View selectedLineView;
        @BindView(R.id.category_name_txt)
        TextView categoryNameTxt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnItemClickBlock {
        void OnItemClickBlock(Category category,int position);
    }
}
