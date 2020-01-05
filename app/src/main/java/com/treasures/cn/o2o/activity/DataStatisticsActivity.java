package com.treasures.cn.o2o.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.treasures.cn.R;
import com.treasures.cn.handler.TreasuresHelp;
import com.treasures.cn.o2o.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.adapter
 * @ClassName: DataStatisticsActivity
 * @Description: java类作用描述 数据统计
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-26 17:34
 */
public class DataStatisticsActivity extends BaseActivity {

    public static void openDataStatisticsActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, DataStatisticsActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.total_count_txt)
    TextView total_count_txt;
    @BindView(R.id.total_out_price_txt)
    TextView total_out_price_txt;
    @BindView(R.id.total_in_price_txt)
    TextView total_in_price_txt;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_data_statistics_layout;
    }

    @OnClick({R.id.back_btn})
    public void backClick(View v) {
        finish();
    }

    @Override
    protected void initView() {
        super.initView();
        TreasuresHelp.getAllTreasures((success, error, data) -> {
            if (data != null && data.size() > 0) {
                total_count_txt.post(() -> total_count_txt.setText(String.valueOf(data.size())));
            }
        });
        TreasuresHelp.getTotalInProce((success, error, data) -> {
            if (data != null && data > 0) {
                total_in_price_txt.post(() -> total_in_price_txt.setText(String.valueOf(data)));
            }
        });
        TreasuresHelp.getTotalOutProce((success, error, data) -> {
            if (data != null && data > 0) {
                total_out_price_txt.post(() -> total_out_price_txt.setText(String.valueOf(data)));
            }
        });
    }
}
