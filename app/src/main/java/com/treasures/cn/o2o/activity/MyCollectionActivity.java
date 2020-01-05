package com.treasures.cn.o2o.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.customView.CostomLinearLayoutManager;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.handler.TreasuresHelp;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.o2o.adapter.TreasuresAdapter;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.fragment
 * @ClassName: MyCollectionActivity
 * @Description: java类作用描述 我的收藏
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-19 01:07
 */
public class MyCollectionActivity extends BaseActivity {

    public static void openMyCollectionActivity(Activity activity, BusiConst.RegisteredStatus status) {
        Intent intent = new Intent();
        intent.setClass(activity, MyCollectionActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.notDataLinear)
    LinearLayout notDataLinear;
    @BindView(R.id.my_recycle)
    RecyclerView homeRecycle;
    @BindView(R.id.my_title_txt)
    TextView my_title_txt;
    @BindView(R.id.clear_btn)
    Button clearBtn;
    private TreasuresAdapter mTreasuresAdapter;
    private TreasuresAdapter.OnTreasuresItemClick mOnTreasuresItemClick = new TreasuresAdapter.OnTreasuresItemClick() {
        @Override
        public void OnLongClick(Treasures treasures) {
        }

        @Override
        public void OnEnshrineClick(Treasures treasures, int position) {
            if (TextUtils.equals(dataType, BusiConst.RecycleStatus.DELETE.toString())) {
                return;
            }
            enshrineTreasures(treasures, position);
        }

        @Override
        public void OnClick(Treasures treasures) {
            if (TextUtils.equals(dataType, BusiConst.RecycleStatus.RECYCLE.toString())) {
                return;
            }
            showRecyclePop(treasures);
        }
    };

    private int pageIndex = 0;
    private List<Treasures> treasuresArr = new ArrayList<>();
    private String dataType = BusiConst.RecycleStatus.RECYCLE.toString();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_collection_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        if (mTreasuresAdapter == null) {
            mTreasuresAdapter = new TreasuresAdapter(getMActivity());
        }
        mTreasuresAdapter.setOnTreasuresItemClick(mOnTreasuresItemClick);
        homeRecycle.setHasFixedSize(true);
        homeRecycle.setItemAnimator(new DefaultItemAnimator());
        homeRecycle.setLayoutManager(new CostomLinearLayoutManager(getMActivity()
                , LinearLayoutManager.VERTICAL, false));
        homeRecycle.setAdapter(mTreasuresAdapter);
        initData();
    }

    private void initData() {
        String type = getIntent().getExtras().getString(Constant.BundleKey.DATA_TYPE);
        if (type == null || TextUtils.isEmpty(type)) {
            finish();
            return;
        }
        homeRecycle.post(() -> {
            dataType = type;
            notDataLinear.setVisibility(View.VISIBLE);
            homeRecycle.setVisibility(View.GONE);
            if (TextUtils.equals(type, BusiConst.RecycleStatus.RECYCLE.toString())) {
                my_title_txt.setText(getMActivity().getString(R.string.my_collection));
                getCollectionTreasures();
                return;
            }
            my_title_txt.setText(getMActivity().getString(R.string.recycle));
            getAllDeleteTreasures();
        });
        clearBtn.post(() -> {
            if (TextUtils.equals(type, BusiConst.RecycleStatus.DELETE.toString())) {
                clearBtn.setVisibility(View.VISIBLE);
            } else {
                clearBtn.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.back_btn, R.id.clear_btn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                getMActivity().finish();
                break;
            case R.id.clear_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(getMActivity());
                builder.setMessage(getMActivity().getString(R.string.confirm_empty));
                builder.setPositiveButton(getMActivity().getString(R.string.confirm), (dialogInterface, i) -> {
                    clearAllDeleteTreasures();
                });
                builder.setNegativeButton(getMActivity().getString(R.string.cancel), (dialogInterface, i) -> {
                });
                builder.show();
                break;
        }
    }

    private void clearAllDeleteTreasures() {
        TreasuresHelp.clearAllDeleteTreasures((success, error, data) -> {
            if (success) {
                getAllDeleteTreasures();
            }
        });
    }

    private void showRecyclePop(Treasures treasures) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getMActivity());
        builder.setMessage(getMActivity().getString(R.string.confirm_recycle));
        builder.setPositiveButton(getMActivity().getString(R.string.confirm), (dialogInterface, i) -> {
            recycleTreasuer(treasures);
        });
        builder.setNegativeButton(getMActivity().getString(R.string.cancel), (dialogInterface, i) -> {
        });
        builder.show();
    }

    private void getCollectionTreasures() {
        TreasuresHelp.getAllCollectionTreasures((success, error, data) -> {
            reloadData(data);
        });
    }

    private void getAllDeleteTreasures() {
        TreasuresHelp.getAllDeleteTreasures(pageIndex, (success, error, data) -> {
            reloadData(data);
        });
    }

    private void reloadData(List<Treasures> data) {
        if (pageIndex == 0) {
            treasuresArr.clear();
        }
        if (data != null && data.size() > 0) {
            treasuresArr.addAll(data);
        }
        if (treasuresArr.size() > 0) {
            mTreasuresAdapter.setList(treasuresArr);
            mTreasuresAdapter.notifyDataSetChanged();
            notDataLinear.setVisibility(View.GONE);
            homeRecycle.setVisibility(View.VISIBLE);
        } else {
            notDataLinear.setVisibility(View.VISIBLE);
            homeRecycle.setVisibility(View.GONE);
        }
    }

    private void recycleTreasuer(Treasures treasures) {
        TreasuresHelp.recycleTreasures(treasures.getId(), (success, error, data) -> {
            if (success) {
                Toast toast = Toast.makeText(mActivity,
                        "回收成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                getAllDeleteTreasures();
            }
        });
    }

    /**
     * 收藏/取消收藏
     * /**
     *
     * @param treasures
     */
    private void enshrineTreasures(Treasures treasures, int position) {
        TreasuresHelp.enshrineTreasires(treasures, (success, error, data) -> {
            if (success) {
                if (data != null) {
                    Toast toast = Toast.makeText(mActivity,
                            data.isEnshrine() ? "已收藏" : "取消收藏", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    getCollectionTreasures();
                }
            }
        });
    }
}
