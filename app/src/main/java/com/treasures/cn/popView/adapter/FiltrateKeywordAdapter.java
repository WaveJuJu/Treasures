package com.treasures.cn.popView.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.treasures.cn.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView.adapter
 * @ClassName: FiltrateKeywordAdapter
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-26 11:55
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-26 11:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FiltrateKeywordAdapter extends RecyclerView.Adapter<FiltrateKeywordAdapter.ViewHolder> {
    private Context mContext;
    private String selectedKey = "";
    private List<String> mList = new ArrayList<>();

    public FiltrateKeywordAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<String> list, String selectedKey) {
        if (list != null && list.size() > 0) {
            this.mList = list;
        }
        this.selectedKey = selectedKey;
    }

    public void setSelectPosition(String selectedKey) {
        this.selectedKey = selectedKey;
        notifyItemRangeChanged(0, mList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_filtrate_keyword_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mList.size() > position) {
            String keyStr = mList.get(position);
            if (!TextUtils.isEmpty(keyStr)) {
                holder.keyword_select_btn.setText(keyStr);
                holder.keyword_select_btn.setSelected(TextUtils.equals(keyStr, selectedKey));
            }
            holder.keyword_select_btn.setOnClickListener(v -> {
                if (onItemClickBlock != null) {
                    onItemClickBlock.OnItemClickBlock(mList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.keyword_select_btn)
        Button keyword_select_btn;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickBlock onItemClickBlock;

    public void setOnItemClickBlock(OnItemClickBlock onItemClickBlock) {
        this.onItemClickBlock = onItemClickBlock;
    }

    public interface OnItemClickBlock {
        void OnItemClickBlock(String keyword);
    }
}
