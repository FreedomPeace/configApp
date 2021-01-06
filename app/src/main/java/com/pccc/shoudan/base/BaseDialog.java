package com.pccc.shoudan.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.techown.merchant.R;

public abstract class BaseDialog {

    private Context mContext;
    private Dialog mDialog;
    private String mType;
    private Callback mCallBack;
    private String mSelectStr;

    public abstract View initView();

    public void showDiaglog(Context context, Callback callback) {
        mContext = context;
        mCallBack = callback;
        if (null != mDialog) {
            mDialog.dismiss();
        } else {
            mDialog = new Dialog(context, R.style.NoTitleDialog);
        }

        mDialog.setContentView(initView());
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void showDiaglog(Context context,String type, String selectStr, Callback callback) {
        mContext = context;
        mType = type;
        mCallBack = callback;
        mSelectStr = selectStr;
        if (null != mDialog) {
            mDialog.dismiss();
        } else {
            mDialog = new Dialog(context, R.style.NoTitleDialog);
        }

        mDialog.setContentView(initView());
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    /**
     * 获取Context对象
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 获取当前页面类型
     * @return
     */
    public String getType() {
        return mType;
    }

    /**
     * 获取选中结果
     * @return
     */
    public String getSelectResult() {
        return mSelectStr;
    }

    public void Dismiss() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    /**
     * 获取画面关闭后的，结果通知对象
     * @return
     */
    public Callback getCallBack() {
        return mCallBack;
    }

    /**
     * 可见性不在这里设置
     *
     * @param content
     * @param show
     * @param star
     */
    public void setValue(String content, TextView show, TextView star) {
        show.setText(content);
        if (TextUtils.isEmpty(content)) {
            if (null != star) {
                star.setTextColor(Color.RED);
                star.setText("*");
            }

        } else {
            if (null != star) {
                star.setText("*");
                star.setTextColor(Color.GREEN);
            }
        }
    }

    public void setStar(String content, TextView star) {
        if (TextUtils.isEmpty(content)) {
            if (null != star) {
                star.setTextColor(Color.RED);
                star.setText("*");
            }
        } else {
            if (null != star) {
                star.setText("*");
                star.setTextColor(Color.GREEN);
            }
        }
    }

    /**
     * dialog关闭回调
     */
    public interface Callback<T> {
        void done(T data);
    }
}
