package com.pccc.shoudan.business.tehui.dialogpage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pccc.shoudan.base.BaseDialog;
import com.pccc.shoudan.business.tehui.adapter.MultiSelectListAdapter;

public class MultiSelectDialog extends BaseDialog implements View.OnClickListener {
    public static final String TYPE_POS_CHANNEL = "1";
    public static final String TYPE_ZHIFU_CHANNEL = "2";
    public static final String TYPE_NOT_APPLY_CHANNEL = "3";
    public static final String TYPE_ONLINE_CHANNEL = "4";

    private MultiSelectListAdapter mAdapter;

    private Button mBtnClose;
    private TextView mTvTitle;
    private RecyclerView mListView;

    private List<String> mListData = new ArrayList<>();

    public View initView() {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View dialogView = (View) factory.inflate(R.layout.dialog_multi_select, null);


        mListView = (RecyclerView) dialogView.findViewById(R.id.multi_select_dialog_layout_listview);
        mTvTitle = (TextView) dialogView.findViewById(R.id.multi_select_dialog_layout_title_name);
        mBtnClose = (Button) dialogView.findViewById(R.id.multi_select_dialog_layout_close_btn);

        updateTitleName();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new MultiSelectListAdapter(getContext(), mListData, getSelectResult());
        mListView.setLayoutManager(manager);
        mListView.setAdapter(mAdapter);

        mBtnClose.setOnClickListener(this);

        return dialogView;
    }

    private void updateTitleName() {
        if (null != getType()) {
            switch (getType()) {
                case TYPE_POS_CHANNEL:
                    mTvTitle.setText("选择POS刷卡适用范围");
                    mListData.clear();
                    Collections.addAll(mListData, new String[]{"芯片卡交易", "PAY类交易", "刷脸交易", "不限"});
                    break;
                case TYPE_ZHIFU_CHANNEL:
                    mTvTitle.setText("选择条码&订单支付适用渠道");
                    mListData.clear();
                    Collections.addAll(mListData, new String[]{"交行渠道", "微信", "支付宝"});
                    break;
                case TYPE_NOT_APPLY_CHANNEL:
                    mTvTitle.setText("选择不适用优惠说明");
                    break;
                case TYPE_ONLINE_CHANNEL:
                    mTvTitle.setText("选择在线使用适用范围");
                    mListData.clear();
                    Collections.addAll(mListData, new String[]{"买单吧", "手机银行快捷"});
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.multi_select_dialog_layout_close_btn:
                StringBuffer result = new StringBuffer();
                if (null != mAdapter) {
                    ArrayList<Integer> selectList = (ArrayList<Integer>) mAdapter.getSelectList();
                    for (int i = 0; i < selectList.size(); i++) {
                        String value = mListData.get(selectList.get(i));
                        if (TextUtils.isEmpty(result.toString())) {
                            result.append(value);
                        } else {
                            result.append("、" + value);
                        }
                    }
                }
                getCallBack().done(result.toString());
                result.delete(0, result.length());
                this.Dismiss();
                break;
        }
    }
}
