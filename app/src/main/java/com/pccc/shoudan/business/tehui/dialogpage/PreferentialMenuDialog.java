package com.pccc.shoudan.business.tehui.dialogpage;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.techown.merchant.R;

import com.pccc.shoudan.base.BaseDialog;

/**
 * 特惠开通项选择画面
 */
public class PreferentialMenuDialog extends BaseDialog implements View.OnClickListener {

    private LinearLayout mItemLayout;
    private Button mBtnClose;

    /**
     * 控件初始化
     * @return
     */
    @Override
    public View initView() {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View dialogView = (View) factory.inflate(R.layout.dialog_preferential_menu, null);

        mItemLayout = (LinearLayout) dialogView.findViewById(R.id.item_layout);
        mBtnClose = (Button) dialogView.findViewById(R.id.item_btn_close);

        mItemLayout.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);

        return dialogView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_btn_close:
                this.Dismiss();
                break;
            case R.id.item_layout:
                PreferentialPayDialog openPreferentialDialog = new PreferentialPayDialog();
                openPreferentialDialog.showDiaglog(getContext(), null);
                break;
        }
    }
}
