package com.treasures.cn.customView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.treasures.cn.R;
import com.treasures.cn.customView.viewInterface.SoftKeyBoardListener;

import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class CenterEdittext extends EditText {
    private Context context;
    private int drawableIcon; // icon图标
    private boolean isShowCenter;//是否居中显示icon，默认为不居中
    private boolean isShowLeft;//键盘打开后icon是否显示在左边，默认为不显示icon
    private boolean isShowHint;//键盘打开后是否显示提示文字,默认为显示
    private boolean isOpen;//是否开启使用,默认为false

    private boolean isDraw = true;//是否绘制,配合居中显示使用
    private String hintText;

    public CenterEdittext(Context context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public CenterEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public CenterEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CenterEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CenterEdittext);
            isShowCenter = array.getBoolean(R.styleable.CenterEdittext_isCenter, false);
            isShowLeft = array.getBoolean(R.styleable.CenterEdittext_isShowLeft, false);
            isShowHint = array.getBoolean(R.styleable.CenterEdittext_isShowHint, true);
            isOpen = array.getBoolean(R.styleable.CenterEdittext_isOpen, true);
            drawableIcon = array.getResourceId(R.styleable.CenterEdittext_drawableIcon, R.mipmap.search);
            array.recycle();
        }
        //  通过监听软键盘显示还是隐藏来设置提示语
        if (context instanceof Activity && isOpen) {
            hintText = getHint().toString();
            SoftKeyBoardListener.setListener((Activity) context, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                @Override
                public void keyBoardShow(int height) {//键盘处于打开状态
                    setCursorVisible(true);// 显示光标
                    isDraw = false;//重新绘制(icon居左或者不显示)
                    if (isShowHint)
                        setHint(hintText);
                    else {
                        if (!TextUtils.isEmpty(hintText))
                            setHint("");
                    }
                }

                @Override
                public void keyBoardHide(int height) {//键盘处于关闭状态
                    setCursorVisible(false);// 隐藏光标
                    if (TextUtils.isEmpty(getText().toString()))
                        isDraw = true;
                    else
                        isDraw = false;
                    if (!TextUtils.isEmpty(hintText))
                        setHint(hintText);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        //如果不启用还使用EditText原来的，否则重新绘制
        if (!isOpen) {
            super.onDraw(canvas);
            return;
        }
        if (isShowCenter && isDraw) {// 将icon绘制在中间
            setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(drawableIcon), null, null, null);//绘制图片
            float textWidth = getPaint().measureText(getHint().toString());//得到文字宽度
            int drawablePadding = getCompoundDrawablePadding();//得到drawablePadding宽度
            int drawableWidth = context.getResources().getDrawable(drawableIcon).getIntrinsicWidth();//得到图片宽度
            float bodyWidth = textWidth + drawableWidth + drawablePadding;//计算距离
            canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);//最终绘制位置
            super.onDraw(canvas);
        } else {
            if (isShowLeft) {
                setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(drawableIcon), null, null, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            super.onDraw(canvas);
        }
    }
}
