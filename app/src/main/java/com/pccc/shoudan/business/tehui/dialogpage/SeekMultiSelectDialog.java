package com.pccc.shoudan.business.tehui.dialogpage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pccc.shoudan.base.BaseDialog;
import com.pccc.shoudan.business.tehui.adapter.SeekMultiSelectListAdapter;

public class SeekMultiSelectDialog extends BaseDialog implements View.OnClickListener {
    public static final String TYPE_ADD_STORES = "1";
    public static final String TYPE_WEEK = "2";

    private SeekMultiSelectListAdapter mAdapter;

    private TextView mTvTitle;//title文言控件
    private TextView mTvSearch;//搜索信息 控件
    private RecyclerView mListView;
    private CheckBox mCheckBox;
    private Button mBtnClose;

    private List<Object> mListData = new ArrayList<>();//原始数据（即所有数据）
    private List<Object> mAdpaterListData = new ArrayList<>();//决定ListView 具体显示数据

    /**
     * 布局控件初始化
     * @return 返回布局
     */
    public View initView() {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View dialogView = (View) factory.inflate(R.layout.dialog_seek_multi_select, null);

        mListView = (RecyclerView) dialogView.findViewById(R.id.seek_multi_select_dialog_layout_listview);
        mTvTitle = (TextView) dialogView.findViewById(R.id.seek_multi_select_dialog_layout_title_name);
        mTvSearch = (TextView) dialogView.findViewById(R.id.seek_multi_select_dialog_layout_search_tv);
        mCheckBox = (CheckBox) dialogView.findViewById(R.id.seek_multi_select_dialog_layout_checkbox);
        mBtnClose = (Button) dialogView.findViewById(R.id.seek_multi_select_dialog_layout_close_btn);

        updateTitleName();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SeekMultiSelectListAdapter(getContext(), mAdpaterListData,
                getType(), //显示的ListView布局文件类型
                updateInitSelectData(),
                new Callback<Boolean>() {//回调 全选属性的变化
                        @Override
                        public void done(Boolean data) {
                            mCheckBox.setChecked(data);
                        }
        });
        mListView.setLayoutManager(manager);
        mListView.setAdapter(mAdapter);

        //全选按钮
        mCheckBox.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);

        return dialogView;
    }

    /**
     * 更新标题，并初始化原始全部数据
     */
    private void updateTitleName() {
        if (null != getType()) {
            switch (getType()) {
                case TYPE_ADD_STORES:
                    mTvTitle.setText("添加门店");
                    break;
                case TYPE_WEEK:
                    mTvTitle.setText("周期限用日期");
                    mListData.clear();
                    Collections.addAll(mListData, new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"});
                    break;
            }
            mAdpaterListData.clear();
            mAdpaterListData.addAll(mListData);
        }
    }

    private List<Integer> updateInitSelectData() {
        ArrayList<Integer> integers = new ArrayList<>();
        String selectResult = getSelectResult();
        switch (getType()) {
            case TYPE_WEEK:
                for (int i = 0; i < mAdpaterListData.size(); i++) {
                    if (selectResult.contains((String)(mAdpaterListData.get(i)))){
                        integers.add(i);
                    }
                }
                break;
        }
        if (integers.size() == mAdpaterListData.size()) {
            mCheckBox.setChecked(true);
        }
        return integers;
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.seek_multi_select_dialog_layout_checkbox://全选按钮点击事件
//                if (mCheckBox.isChecked()) {
//                    mAdapter.setIsCheckAll(true);
//                } else {
//                    mAdapter.setIsCheckAll(false);
//                }
//                break;
//            case R.id.seek_multi_select_dialog_layout_search_tv://输入框点击事件
//                DialogUtil.newEditDialog(
//                        getContext(), "请输入活动名称", "",
//                        mTvSearch.getText().toString(),
//                        ValidatorUtil.validator(ValidatorUtil.CASE_LENGTH, 20),
//                        new DialogUtil.Callback() {
//                            @Override
//                            public void done(String data) {//文字变化，更新数据源
//                                mTvSearch.setText(data);
//                                searchTextChange();
//                            }
//                        });
//                break;
//            case R.id.seek_multi_select_dialog_layout_close_btn://返回按钮点击事件，并将选中结果返回至上个画面
//                List<Integer> selectList = mAdapter.getSelectList();
//                if (null != getType()) {
//                    switch (getType()) {
//                        case TYPE_ADD_STORES:
//                            break;
//                        case TYPE_WEEK:
//                            StringBuffer result = new StringBuffer();
//                            if (mCheckBox.isChecked()) {
//                                for (int i = 0; i < mAdpaterListData.size(); i++) {
//                                    String value = (String) (mAdpaterListData.get(i));
//                                    if (TextUtils.isEmpty(result.toString())) {
//                                        result.append(value);
//                                    } else {
//                                        result.append("|" + value);
//                                    }
//                                }
//                            } else {
//                                for (int i = 0; i < selectList.size(); i++) {
//                                    String value = (String) (mAdpaterListData.get(selectList.get(i)));
//                                    if (TextUtils.isEmpty(result.toString())) {
//                                        result.append(value);
//                                    } else {
//                                        result.append("|" + value);
//                                    }
//                                }
//                            }
//                            getCallBack().done(result.toString());
//                            Log.d("test", "onClick: res = " + result.toString());
//                            break;
//                    }
//                }
//                Dismiss();
//                break;
//        }
    }

    /**
     * 搜索内容变化，更新填充ListView的数据源
     */
    private void searchTextChange() {
        if (null != mListData) {
            String searchText = mTvSearch.getText().toString();
            mAdpaterListData.clear();
            if (TextUtils.isEmpty(searchText)) {
                mAdpaterListData.addAll(mListData);
            } else {
                for (int i = 0; i < mListData.size(); i++) {
                    if (((String)(mListData.get(i))).contains(searchText)) {
                        mAdpaterListData.add(mListData.get(i));
                    }
                }
            }
            mAdapter.updateList(mAdpaterListData);
        }
    }
}
