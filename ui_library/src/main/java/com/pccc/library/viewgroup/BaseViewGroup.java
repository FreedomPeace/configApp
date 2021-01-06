package com.pccc.library.viewgroup;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


//import com.google.android.material.snackbar.Snackbar;
import com.pccc.library.R;
import com.pccc.library.dialog.DialogUtil;


/**
 * <p>
 * 新增abstract class ViewCallback implements DialogUtil.Callback，用于callback回调时传回view
 * 2020/11/4
 * </p>
 * created by liliangjun
 * 2020/10/27
 */
public abstract class BaseViewGroup extends LinearLayout {
    protected TextView mark;
    protected TextView tag;
    protected LinearLayout container;
    private int drawableId = 0;
    private int drawableErrorId = 0;
    private int color = Color.WHITE;
    private int colorError = Color.RED;


    protected int screenWidthPx = getContext().getResources().getDisplayMetrics().widthPixels;
    protected float widthScaleFactor = 1.0f;
    protected int screenHeightPx = getContext().getResources().getDisplayMetrics().heightPixels;
    protected float heightScaleFactor = 1.0f;
    protected int colorMark = Color.RED;
    protected int colorOK = Color.GREEN;
    protected boolean showMark = false;
    protected DialogUtil.Callback callback;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initScaleFactor();
        findViews();
        initStyle(attrs);
        initUI();
        requestLayout();
    }


    /**
     * <p>
     * 根据{@link #getLayoutId()}获取view及view内部的控件
     * 子类重写时需调用super()
     * </p>
     */
    protected void findViews() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        mark = findViewById(R.id.mark);
        tag = findViewById(R.id.tag);
        container = findViewById(R.id.container);
        findActionView();
    }

    /**
     * <p>获取actionView的id</p>
     */
    protected abstract void findActionView();

    /**
     * 返回layoutId用于{@link BaseViewGroup#findViews()} 获取view
     *
     * @return layoutId
     */
    protected abstract int getLayoutId();


    /**
     * 获取自定义样式
     *
     * @param attrs
     */
    protected abstract void initStyle(AttributeSet attrs);

    /**
     * 设置ui及对应的事件
     */
    protected abstract void initUI();

    /**
     * 日志
     */
    protected abstract void log();

    /**
     * 点击actionView后的回调
     *
     * @param callback {}
     */
    public void setOnClickCallback(DialogUtil.Callback callback) {
        this.callback = callback;
    }

    /**
     * 设置*标的状态
     *
     * @param isOK
     */
    public void markOK(boolean isOK) {
        mark.setTextColor(isOK ? colorOK : colorMark);
    }

    /**
     * 设置Tag的内容
     *
     * @param text
     */
    public void setTag(String text) {
        tag.setText(text);
    }

    /**
     * 是否显示*标
     *
     * @param showMark
     */
    public abstract void showMark(boolean showMark);

    /**
     * 显示提示信息
     *
     * @param message
     */
    protected void showMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    /**
     * 设置宽高拉伸因子
     */
    protected void initScaleFactor() {
//        widthScaleFactor = 1920.0f / screenWidthPx;
//        heightScaleFactor = 1080.0f / screenHeightPx;
    }

    /**
     * px转sp
     *
     * @param pxValue 像素大小
     * @return 字体大小sp
     */
    protected int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px 像素大小
     * @return dp
     */
    protected int px2dp(int px) {
        return (int) (px / getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public abstract void requestErrorFocus(boolean isError);

    public abstract static class ViewCallback implements DialogUtil.Callback {

        @Override
        public void done(String data) {
            //nothing to do
        }

        public abstract void done(View v, String data);
    }

}
