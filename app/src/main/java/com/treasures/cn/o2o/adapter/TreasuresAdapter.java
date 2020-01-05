package com.treasures.cn.o2o.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.treasures.cn.R;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.handler.CategoryHelp;
import com.treasures.cn.utils.ImageUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TreasuresAdapter extends RecyclerView.Adapter<TreasuresAdapter.ViewHolder> {
    private List<Treasures> mList = new ArrayList<>();
    private Context mContext;

    public interface OnTreasuresItemClick {
        void OnLongClick(Treasures treasures);//长按事件回调

        void OnEnshrineClick(Treasures treasures, int position);//收藏事件回调

        void OnClick(Treasures treasures);
    }

    private OnTreasuresItemClick onTreasuresItemClick;

    public void setOnTreasuresItemClick(OnTreasuresItemClick onTreasuresItemClick) {
        this.onTreasuresItemClick = onTreasuresItemClick;
    }

    public TreasuresAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<Treasures> list) {
        if (list != null && list.size() > 0) {
            this.mList = list;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_treasures_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position, @NotNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            reloadItem(holder, position);
        }
    }

    private String saleStatus(int type) {
        if (type == -1) {
            return mContext.getString(R.string.cant_sale);
        } else if (type == 0) {
            return mContext.getString(R.string.can_sale);
        } else {
            return mContext.getString(R.string.yet_sale);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
    }

    @SuppressLint("SetTextI18n")
    private void reloadItem(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (mList.size() > position) {
            holder.end_view.setVisibility(position == mList.size() - 1 ? View.VISIBLE : View.GONE);
            Treasures treasures = mList.get(position);
            holder.treasuresContentLinear.setOnLongClickListener(v -> {
                if (onTreasuresItemClick != null) {
                    onTreasuresItemClick.OnLongClick(treasures);
                }
                return false;
            });
            holder.treasuresContentLinear.setOnClickListener(v -> {
                if (onTreasuresItemClick != null) {
                    onTreasuresItemClick.OnClick(treasures);
                }
            });
            holder.collection_img.setOnClickListener(v -> {
                if (onTreasuresItemClick != null) {
                    onTreasuresItemClick.OnEnshrineClick(treasures, position);
                }
            });
            holder.treasures_name_txt.setText(treasures.getSubTitle());
            holder.img_count_txt.setVisibility(View.GONE);
            if (treasures.getImages().size() > 0) {
                holder.notImgTxt.setVisibility(View.GONE);
                String path = treasures.getImages().get(0);
                Bitmap mBitmap = ImageUtils.getScaleBitmap(mContext, path);
                holder.treasures_img.setImageBitmap(mBitmap);
                holder.img_count_txt.setVisibility(treasures.getImages().size() <= 1 ? View.GONE : View.VISIBLE);
                if (treasures.getImages().size() > 1) {
                    holder.img_count_txt.setVisibility(View.VISIBLE);
                    holder.img_count_txt.setText("x" + treasures.getImages().size());
                }
            } else {
                holder.treasures_img.setImageResource(R.color.gray_ed);
                holder.notImgTxt.setVisibility(View.VISIBLE);
            }
            holder.treasures_desc_txt.setText(treasures.getDescribe());
            if (treasures.getKeywordsArr().size() > 0) {
                holder.keywords1_txt.setVisibility(View.VISIBLE);
                holder.keywords1_txt.setText(treasures.getKeywordsArr().get(0));
                if (treasures.getKeywordsArr().size() > 1) {
                    holder.keywords2_txt.setVisibility(View.VISIBLE);
                    holder.keywords2_txt.setText(treasures.getKeywordsArr().get(1));
                    if (treasures.getKeywordsArr().size() > 2) {
                        holder.keywords3_txt.setVisibility(View.VISIBLE);
                        holder.keywords3_txt.setText(treasures.getKeywordsArr().get(2));
                    } else {
                        holder.keywords3_txt.setVisibility(View.GONE);
                    }
                } else {
                    holder.keywords2_txt.setVisibility(View.GONE);
                    holder.keywords3_txt.setVisibility(View.GONE);
                }
            } else {
                holder.keywords1_txt.setVisibility(View.GONE);
                holder.keywords2_txt.setVisibility(View.GONE);
                holder.keywords3_txt.setVisibility(View.GONE);
            }

            holder.sale_status_txt.setText(saleStatus(treasures.getSoldType()));
            holder.collection_img.setImageResource(treasures.isEnshrine() ? R.mipmap.collection : R.mipmap.collection_un);
            CategoryType categoryType = CategoryHelp.getCategoryById(treasures.getCategoryTypeId());
            holder.category_name_txt.setText(categoryType == null ? "" : categoryType.getName());
            holder.add_time_txt.setText(treasures.getCreateTime());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.treasures_content_linear)
        LinearLayout treasuresContentLinear;
        @BindView(R.id.treasures_img)
        RoundedImageView treasures_img;
        @BindView(R.id.img_count_txt)
        TextView img_count_txt;
        @BindView(R.id.treasures_name_txt)
        TextView treasures_name_txt;
        @BindView(R.id.treasures_desc_txt)
        TextView treasures_desc_txt;

        @BindView(R.id.keywords1_txt)
        TextView keywords1_txt;
        @BindView(R.id.keywords2_txt)
        TextView keywords2_txt;
        @BindView(R.id.keywords3_txt)
        TextView keywords3_txt;
        @BindView(R.id.sale_status_txt)
        TextView sale_status_txt;
        @BindView(R.id.category_name_txt)
        TextView category_name_txt;
        @BindView(R.id.collection_img)
        ImageView collection_img;
        @BindView(R.id.add_time_txt)
        TextView add_time_txt;
        @BindView(R.id.notImgTxt)
        TextView notImgTxt;
        @BindView(R.id.end_view)
        View end_view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
