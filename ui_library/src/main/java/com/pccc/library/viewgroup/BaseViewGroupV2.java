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
 * created by liliangjun
 * 2020/10/27
 */
public abstract class BaseViewGroupV2<T extends View> extends LinearLayout {
    protected TextView mark;
    protected TextView tag;
    protected T actionView;
    protected LinearLayout container;


    protected int screenWidthPx = getContext().getResources().getDisplayMetrics().widthPixels;
    protected float widthScaleFactor;
    protected int screenHeightPx = getContext().getResources().getDisplayMetrics().heightPixels;
    protected float heightScaleFactor;
    protected int colorMark = Color.RED;
    protected int colorOK = Color.GREEN;
    protected boolean showMark = false;
    protected DialogUtil.Callback callback;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroupV2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroupV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroupV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initScaleFactor();
        findViews();
        initStyle(attrs);
        initUI();
        requestLayout();
    }


    /**
     * Call super()
     */
    protected void findViews() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        mark = findViewById(R.id.mark);
        tag = findViewById(R.id.tag);
        container = findViewById(R.id.container);
        findActionView();
    }

    protected abstract void findActionView();

    protected abstract int getLayoutId();


    protected abstract void initStyle(AttributeSet attrs);

    protected abstract void initUI();

    protected abstract void performAction();

    protected T actionView() {
        return actionView;
    }

    protected void setOnClickCallback(DialogUtil.Callback callback) {
        this.callback = callback;
    }

    public void markOK(boolean isOK) {
        mark.setTextColor(isOK ? colorOK : colorMark);
    }

    public void setTag(String text) {
        tag.setText(text);
    }

    public abstract void showMark(boolean showMark);

    protected void showMessage(String message) {
//        try {
//            Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
//        } catch (Exception e) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//        }
    }

    protected void initScaleFactor() {
        widthScaleFactor = screenWidthPx / 1920.0f;
        heightScaleFactor = screenHeightPx / 1080.0f;
    }

    protected int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    protected int px2dp(int px) {
        return (int) (px / getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

}
