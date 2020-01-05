package com.treasures.cn.o2o

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.treasures.cn.utils.HideIMEUtil

/**
 * Created by zhutao on 17/9/15.
 * 所有activity的夫类
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutResId: Int
    protected lateinit var mActivity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar?.hide()
        setContentView(layoutResId)
        mActivity = this
        ButterKnife.bind(mActivity)
        HideIMEUtil.wrap(this)
        mActivity
        initView()
    }
    /**
     * 初始化view
     */
    protected open fun initView() {}
}