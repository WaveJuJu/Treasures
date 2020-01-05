package com.treasures.cn.o2o.fragment;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.customView.CenterEdittext;
import com.treasures.cn.customView.CostomLinearLayoutManager;
import com.treasures.cn.customView.DrawableRightCenterButton;
import com.treasures.cn.entity.FuzzySearch;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.TreasuresHelp;
import com.treasures.cn.o2o.BaseFragment;
import com.treasures.cn.o2o.activity.TreasureDetailActivity;
import com.treasures.cn.o2o.adapter.TreasuresAdapter;
import com.treasures.cn.popView.ConfirmDeletePopView;
import com.treasures.cn.popView.FiltrateCategoryPopView;
import com.treasures.cn.popView.FiltrateKeywordPopView;
import com.treasures.cn.popView.ModifyTreasuresPopView;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.Cn2Spell;
import com.treasures.cn.utils.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    public static final String INTERFACE_NAME = HomeFragment.class.getName() + "FUNCTION";

    @BindView(R.id.home_search_edit)
    CenterEdittext homeSearchEdit;
    TreasuresAdapter mTreasuresAdapter;
    @BindView(R.id.home_recycle)
    RecyclerView homeRecycle;
    @BindView(R.id.notDataLinear)
    LinearLayout notDataLinear;
    @BindView(R.id.not_data_txt)
    TextView notDataTxt;

    @BindView(R.id.add_treasures_btn)
    ImageButton add_treasures_btn;

    @BindView(R.id.year_txt)
    TextView year_txt;
    @BindView(R.id.year_img1)
    ImageView year_img1;
    @BindView(R.id.year_img2)
    ImageView year_img2;
    @BindView(R.id.yeat_line_view)
    View yeat_line_view;

    @BindView(R.id.price_txt)
    TextView price_txt;
    @BindView(R.id.price_img1)
    ImageView price_img1;
    @BindView(R.id.price_img2)
    ImageView price_img2;
    @BindView(R.id.price_line_view)
    View price_line_view;

    @BindView(R.id.filter_txt)
    TextView filter_txt;
    @BindView(R.id.filter_img)
    ImageView filter_img;
    @BindView(R.id.filter_line_view)
    View filter_line_view;

    @BindView(R.id.can_sale_btn)
    Button canSaleBtn;
    @BindView(R.id.cant_sale_btn)
    Button cantSaleBtn;
    @BindView(R.id.yet_sale_btn)
    Button yetSaleBtn;
    @BindView(R.id.category_name_btn)
    DrawableRightCenterButton categoryNameBtn;
    @BindView(R.id.long_hint_linear)
    LinearLayout long_hint_linear;

    @BindView(R.id.advertis_wb)
    WebView  advertisWb;
    private FuzzySearch mFuzzySearch = new FuzzySearch();
    private int pageIndex = 0;
    private List<Treasures> treasuresArr = new ArrayList<>();
    private TreasuresAdapter.OnTreasuresItemClick mOnTreasuresItemClick = new TreasuresAdapter.OnTreasuresItemClick() {
        @Override
        public void OnLongClick(Treasures treasures) {
            showModifyPop(treasures);
        }

        @Override
        public void OnEnshrineClick(Treasures treasures, int position) {
            enshrineTreasures(treasures, position);
        }

        @Override
        public void OnClick(Treasures treasures) {
            TreasureDetailActivity.openTreasureDetailActivity(getMActivity(), treasures);
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        if (mTreasuresAdapter == null) {
            mTreasuresAdapter = new TreasuresAdapter(getContext());
        }
        mTreasuresAdapter.setOnTreasuresItemClick(mOnTreasuresItemClick);
        homeRecycle.setHasFixedSize(true);
        homeRecycle.setItemAnimator(new DefaultItemAnimator());
        homeRecycle.setLayoutManager(new CostomLinearLayoutManager(getMActivity()
                , LinearLayoutManager.VERTICAL, false));
        homeRecycle.setAdapter(mTreasuresAdapter);
        homeRecycle.post(() -> {
            notDataLinear.setVisibility(View.VISIBLE);
            homeRecycle.setVisibility(View.GONE);
            homeSearchEdit.addTextChangedListener(new ClassOfTextWatcher(homeSearchEdit));
            fuzzySearchTreasures();
        });
        initWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
        fuzzySearchTreasures();
        notDataTxt.post(() -> {
            if (MemoryData.selectedCategoryTypes.size() <= 0) {
                notDataTxt.setText(getActivity().getString(R.string.categoty_hint));
            } else {
                notDataTxt.setText(getActivity().getString(R.string.home_hint));
            }
        });
    }

    @OnClick({R.id.add_treasures_btn, R.id.year_linear, R.id.filter_linear
            , R.id.price_linear, R.id.can_sale_btn, R.id.cant_sale_btn
            , R.id.yet_sale_btn, R.id.category_name_btn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.can_sale_btn:
                canSaleBtn.post(() -> {
                    canSaleBtn.setSelected(!canSaleBtn.isSelected());
                    cantSaleBtn.setSelected(false);
                    yetSaleBtn.setSelected(false);
                    mFuzzySearch.setSoldType(canSaleBtn.isSelected() ? 0 : -2);
                    fuzzySearchTreasures();
                });
                break;
            case R.id.cant_sale_btn:
                cantSaleBtn.post(() -> {
                    cantSaleBtn.setSelected(!cantSaleBtn.isSelected());
                    canSaleBtn.setSelected(false);
                    yetSaleBtn.setSelected(false);
                    mFuzzySearch.setSoldType(cantSaleBtn.isSelected() ? -1 : -2);
                    fuzzySearchTreasures();
                });

                break;
            case R.id.yet_sale_btn:
                yetSaleBtn.post(() -> {
                    yetSaleBtn.setSelected(!yetSaleBtn.isSelected());
                    canSaleBtn.setSelected(false);
                    cantSaleBtn.setSelected(false);
                    mFuzzySearch.setSoldType(yetSaleBtn.isSelected() ? 1 : -2);
                    fuzzySearchTreasures();
                });
                break;
            case R.id.add_treasures_btn:
                add_treasures_btn.post(() -> {
                    if (MemoryData.selectedCategoryTypes.size() <= 0) {
                        Toast.makeText(mActivity, mActivity.getString(R.string.categoty_none_hint), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (lastClick()){
                        Bundle bundleAdd = new Bundle();
                        bundleAdd.putString(Constant.BundleKey.MODIFY_TYPE, BusiConst.ModifyType.NEW_ADD.toString());
                        Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_modifyTreasuresActivity, bundleAdd);
                    }
                });
                break;
            case R.id.year_linear:
                yeat_line_view.post(() -> {
                    yeat_line_view.setVisibility(View.VISIBLE);
                    price_line_view.setVisibility(View.GONE);
                    year_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    price_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mFuzzySearch.setPriceStatus(-1);
                    if (mFuzzySearch.getYearStatus() == -1) {
                        mFuzzySearch.setYearStatus(0);//降序
                        year_img1.setVisibility(View.GONE);
                        year_img2.setVisibility(View.VISIBLE);
                    } else if (mFuzzySearch.getYearStatus() == 0) {
                        mFuzzySearch.setYearStatus(1);//升序
                        year_img1.setVisibility(View.VISIBLE);
                        year_img2.setVisibility(View.GONE);
                    } else {
                        mFuzzySearch.setYearStatus(-1);//取消
                        year_img1.setVisibility(View.VISIBLE);
                        year_img2.setVisibility(View.VISIBLE);
                        yeat_line_view.setVisibility(View.GONE);
                    }
                    fuzzySearchTreasures();
                });
                break;
            case R.id.price_linear:
                price_line_view.post(() -> {
                    yeat_line_view.setVisibility(View.GONE);
                    price_line_view.setVisibility(View.VISIBLE);
                    year_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    price_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    mFuzzySearch.setYearStatus(-1);
                    if (mFuzzySearch.getPriceStatus() == -1) {
                        mFuzzySearch.setPriceStatus(0);//降序
                        price_img1.setVisibility(View.GONE);
                        price_img2.setVisibility(View.VISIBLE);
                    } else if (mFuzzySearch.getPriceStatus() == 0) {
                        mFuzzySearch.setPriceStatus(1);//升序
                        price_img1.setVisibility(View.VISIBLE);
                        price_img2.setVisibility(View.GONE);
                    } else {
                        mFuzzySearch.setPriceStatus(-1);//取消
                        price_img1.setVisibility(View.VISIBLE);
                        price_img2.setVisibility(View.VISIBLE);
                        price_line_view.setVisibility(View.GONE);
                    }
                    fuzzySearchTreasures();
                });
                break;
            case R.id.filter_linear:
                filter_line_view.post(this::showFiltrateKeywordPop);
                break;
            case R.id.category_name_btn:
                showFiltrateCategoryPop();
                break;
        }
    }

    private void showFiltrateKeywordPop() {
        TreasuresHelp.getAllTreasuresKeywordsType((success, error, data) -> {
            if (data == null || data.size() <= 0) {
                return;
            }

            FiltrateKeywordPopView popView = new FiltrateKeywordPopView(getMActivity(), mFuzzySearch.getKeyword(), R.id.filter_linear);
            filter_img.setImageResource(R.mipmap.up1);
            popView.setOnItemClickBlock(new FiltrateKeywordPopView.OnItemClickBlock() {
                @Override
                public void OnItemClickBlock(String keyword) {
                    mFuzzySearch.setKeyword(keyword);
                    fuzzySearchTreasures();
                }
                @Override
                public void OnDismissListener() {
                    filter_img.setImageResource(R.mipmap.down1);
                }
            });
            popView.show(data);
        });

    }

    private void showFiltrateCategoryPop() {
        TreasuresHelp.getAllTreasureCategoryType((success, error, data) -> {
            if (data != null && data.size() > 0) {
                FiltrateCategoryPopView filtrateCategoryPopView = new FiltrateCategoryPopView(getActivity(), R.id.category_name_btn);
                filtrateCategoryPopView.show(data, mFuzzySearch.getCategoryTypeId());
                filtrateCategoryPopView.setOnFiltrateCategoryBlock(categoryType -> {
                    cantSaleBtn.setSelected(false);
                    yetSaleBtn.setSelected(false);
                    canSaleBtn.setSelected(false);
                    clearFuzzySearch();
                    if (categoryType.getId() == mFuzzySearch.getCategoryTypeId()) {
                        mFuzzySearch.setCategoryTypeId(0);
                    } else {
                        mFuzzySearch.setCategoryTypeId(categoryType.getId());
                    }
                    fuzzySearchTreasures();
                });
                return;
            }
            Toast.makeText(mActivity, mActivity.getString(R.string.filter_hint), Toast.LENGTH_SHORT).show();
        });
    }

    private void clearFuzzySearch() {
        mFuzzySearch.setSoldType(-2);
        mFuzzySearch.setKeyword("");
        mFuzzySearch.setSearchContent("");
        mFuzzySearch.setYearStatus(-1);
        mFuzzySearch.setPriceStatus(-1);
    }

    /**
     * 显示操作弹窗
     */
    private void showModifyPop(Treasures treasures) {
        ModifyTreasuresPopView modifyTreasuresPopView = new ModifyTreasuresPopView(mActivity, R.id.main_rela);
        modifyTreasuresPopView.show();
        modifyTreasuresPopView.setModifyTreasuresBlock((view, action) -> {
            switch (action) {
                case Constant.ModifyAction.MODIFY:
                    if (lastClick()){
                        Bundle bundleModify = new Bundle();
                        bundleModify.putSerializable(Constant.BundleKey.MODIFY_TREASURES, treasures);
                        bundleModify.putString(Constant.BundleKey.MODIFY_TYPE, BusiConst.ModifyType.MODIFY.toString());
                        Navigation.findNavController(add_treasures_btn)
                                .navigate(R.id.action_navigation_home_to_modifyTreasuresActivity, bundleModify);
                    }
                    break;
                case Constant.ModifyAction.DELETE:
                    showConfirmDeletePop(treasures);
                    break;
                case Constant.ModifyAction.COPY:
                    Date date = new Date();
                    Bundle bundleCopy = new Bundle();
                    treasures.setId(String.valueOf(date.getTime()));
                    bundleCopy.putSerializable(Constant.BundleKey.MODIFY_TREASURES, treasures);
                    bundleCopy.putString(Constant.BundleKey.MODIFY_TYPE, BusiConst.ModifyType.COPY.toString());
                    Navigation.findNavController(add_treasures_btn)
                            .navigate(R.id.action_navigation_home_to_modifyTreasuresActivity, bundleCopy);
                    break;
            }
        });
    }

    /**
     * 显示确认删除弹窗
     */
    private void showConfirmDeletePop(Treasures treasures) {
        ConfirmDeletePopView confirmHandlePopView = new ConfirmDeletePopView(mActivity, R.id.main_rela);
        confirmHandlePopView.show(getActivity().getString(R.string.confirm_delete));
        confirmHandlePopView.setClickListener(new ConfirmDeletePopView.OnHintManagerClickListener() {
            @Override
            public void OnCancelClickListener() {
                confirmHandlePopView.close();
            }

            @Override
            public void OnConfirmClickListener() {
                confirmHandlePopView.close();
                deleteTreasures(treasures);
            }
        });
    }

    /**
     * 删除Treasures
     *
     * @param treasures
     */
    private void deleteTreasures(Treasures treasures) {
        TreasuresHelp.deleteTreasures(treasures.getId(), (success, error, data) -> {
            if (success) {
                fuzzySearchTreasures();
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
                    fuzzySearchTreasures();
                }
            }
        });
    }

    private class ClassOfTextWatcher implements TextWatcher {
        private TextView view;

        public ClassOfTextWatcher(View view) {

            if (view instanceof TextView)
                this.view = (TextView) view;
            else
                throw new ClassCastException(
                        "view must be an instance Of TextView");
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchSre = Cn2Spell.getPinYin(s.toString());
            mFuzzySearch.setSearchContent(searchSre);
            fuzzySearchTreasures();
        }
    }

    private void fuzzySearchTreasures() {
        TreasuresHelp.fuzzySearchTreasures(mFuzzySearch, pageIndex, (success, error, data) -> {
            if (success) {
                homeRecycle.post(() -> {
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
                        long_hint_linear.setVisibility(treasuresArr.size() == 1 ? View.VISIBLE:View.GONE);
                    } else {
                        notDataLinear.setVisibility(View.VISIBLE);
                        homeRecycle.setVisibility(View.GONE);
                        long_hint_linear.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void initWebView(){
        WebSettings settings = advertisWb.getSettings();
        settings.setDomStorageEnabled(true);/*DOM存储API是否可用，默认false。*/
        settings.setAllowFileAccess(true);/*是否允许访问文件，默认允许。*/
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);/*WebView是否下载图片资源，默认为true。*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            settings.setLoadWithOverviewMode(true);//适应屏幕
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把所有内容放大webview等宽的一列中
        }
        settings.setNeedInitialFocus(false);///当webview调用requestFocus时为webview设置节点
        settings.setBlockNetworkLoads(false);/*是否禁止从网络下载数据，如果app有INTERNET权限，默认值为false，否则默认为true*/
        settings.setJavaScriptCanOpenWindowsAutomatically(true);/*让JavaScript自动打开窗口，默认false。适用于JavaScript方法window.open()。*/
        settings.setAllowFileAccessFromFileURLs(true);/*是否允许运行在一个URL环境*/
        settings.setAllowUniversalAccessFromFileURLs(true);/*是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容*/
        advertisWb.post(()-> advertisWb.loadUrl("http://www.scgj.de/all"));
        advertisWb.setVisibility(View.VISIBLE);
        if (MemoryData.USER_INFO.getInviteCodeArr()!= null &&
                MemoryData.USER_INFO.getInviteCodeArr().size() >= 3){
            advertisWb.setVisibility(View.GONE);
        }
    }
}
