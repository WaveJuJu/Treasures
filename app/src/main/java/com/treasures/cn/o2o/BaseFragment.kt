package com.treasures.cn.o2o

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder


abstract class BaseFragment : Fragment(), View.OnTouchListener {
    private var mLastClickTime: Long = 0
    val TIME_INTERVAL = 1000L
    protected lateinit var mActivity: BaseActivity
    abstract val layoutResId: Int
    private var unbinder: Unbinder? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
//        mActivity.onWindowFocusChanged(false)
    }

    internal var view: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view != null) {
            val parent = view?.parent as? ViewGroup
            parent?.removeView(view)
        } else {
            view = inflater.inflate(layoutResId, null)
        }
        view!!.setOnTouchListener(this)
        unbinder = ButterKnife.bind(this, view!!)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (unbinder != null) {
            unbinder!!.unbind()
        }

    }

    fun lastClick():Boolean{
        val nowTime = System.currentTimeMillis()
        if (nowTime - mLastClickTime > TIME_INTERVAL) { // do something
            mLastClickTime = nowTime
            return true
        }
        return false
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return true
    }

    override fun onStart() {
        super.onStart()
        mActivity.onWindowFocusChanged(true)
    }

    /**
     * 初始化view
     */
    protected open fun initView() {
    }
}