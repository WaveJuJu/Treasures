package com.treasures.cn.popView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treasures.cn.R;
import com.treasures.cn.entity.CategoryType;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView.adapter
 * @ClassName: FiltrateCategoryAdapter
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-18 12:28
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-18 12:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FiltrateCategoryAdapter extends RecyclerView.Adapter<FiltrateCategoryAdapter.ViewHolder> {
    private List<CategoryType> mList = new ArrayList<>();
    private Context mContext;
    private OnItemClickBlock itemClickBlock;
    private int selectedId = 0;
    public void setItemClickBlock(OnItemClickBlock itemClickBlock) {
        this.itemClickBlock = itemClickBlock;
    }
    public FiltrateCategoryAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<CategoryType> list,int selectedId) {
        if (list != null && list.size() > 0){
            this.mList = list;
        }
        this.selectedId = selectedId;
    }

    public void setSelectPosition(int selectPosition) {
        notifyItemRangeChanged(0, mList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_filtrate_gategory_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryType category = mList.get(position);
        holder.categoryNameTxt.setText(category.getName());
        holder.categoryNameTxt.setTextColor(ContextCompat.getColor(mContext
                , category.getId() == selectedId ? R.color.main_color : R.color.title_clolor));
        holder.categoryNameTxt.setOnClickListener(v -> {
            if (itemClickBlock != null) {
                itemClickBlock.OnItemClickBlock(category, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.filtrate_type_name_txt)
        TextView categoryNameTxt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnItemClickBlock {
        void OnItemClickBlock(CategoryType categoryType, int position);
    }
}
