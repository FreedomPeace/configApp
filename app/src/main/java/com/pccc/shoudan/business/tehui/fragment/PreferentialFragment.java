package com.pccc.shoudan.business.tehui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techown.merchant.R;

import com.pccc.shoudan.business.tehui.adapter.PreferentialInfoListAdapter;
import com.pccc.shoudan.business.tehui.dialogpage.PreferentialMenuDialog;

/**
 * 特惠买单主画面
 */
public class PreferentialFragment extends Fragment {

    //控件View
    private Button mBtnAdd, mBtnPre, mBtnSave, mBtnNext;
    private LinearLayout mLayoutInfoTitle, mLayoutBtnPre;
    private RecyclerView mListView;
    private TextView mTvNoAdd;

    //对象
    private MyClickListener mClickListener;
    private PreferentialInfoListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pf, null);

        mClickListener = new MyClickListener();
        mAdapter = new PreferentialInfoListAdapter();

        initView(view);
        return view;
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view) {
        if (null == view) {
            return;
        }
        mTvNoAdd = (TextView) view.findViewById(R.id.fragment_preferntial_tv_noadd);
        mLayoutInfoTitle = (LinearLayout) view.findViewById(R.id.fragment_preferntial_layout_infotitle);
        mLayoutBtnPre = (LinearLayout) view.findViewById(R.id.fragment_preferntial_layout_pre);

        mBtnAdd = (Button) view.findViewById(R.id.fragment_preferntial_btn_add);
        mBtnPre = (Button) view.findViewById(R.id.fragment_preferntial_btn_pre);
        mBtnSave = (Button) view.findViewById(R.id.fragment_preferntial_btn_save);
        mBtnNext = (Button) view.findViewById(R.id.fragment_preferntial_btn_next);

        mListView = (RecyclerView) view.findViewById(R.id.fragment_preferntial_list_info);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(manager);
        mListView.setAdapter(mAdapter);

        mBtnAdd.setOnClickListener(mClickListener);
        mBtnPre.setOnClickListener(mClickListener);
        mBtnSave.setOnClickListener(mClickListener);
        mBtnNext.setOnClickListener(mClickListener);
    }

    /**
     *
     */
    public void getData() {
        //TODO 获取活动数据，更新ListView,更新画面
        updateListVisibility();
    }

    /**
     * 更新画面添加特惠活动
     */
    private void updateListVisibility() {
        if (true) {//TODO 是否存在数据
            mTvNoAdd.setVisibility(View.GONE);
            mLayoutInfoTitle.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.VISIBLE);
        } else {
            mTvNoAdd.setVisibility(View.VISIBLE);
            mLayoutInfoTitle.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
        }
    }

    /**
     * 根据商户是否需要签署授权书，来确定“下一步”具体的显示和动作
     */
    private void updatePreBtnState() {
        if (true) {
            //TODO 商户需要签署授权书
            mLayoutBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setText("下一步");
        } else {
            mLayoutBtnPre.setVisibility(View.GONE);
            mBtnNext.setText("提交审核");
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fragment_preferntial_btn_add:
                    //迁移画面
                    PreferentialMenuDialog addPreferentialDialog = new PreferentialMenuDialog();
                    addPreferentialDialog.showDiaglog(getContext(), null);
                    break;
                case R.id.fragment_preferntial_btn_pre:
                    //TODO 切换到 特约业务开设画面
                    break;
                case R.id.fragment_preferntial_btn_save:
                    //TODO 保存数据，并返回至主页面
                    break;
                case R.id.fragment_preferntial_btn_next:
                    //TODO 根据商户是否需要签署授权书
                    //1.如果需要签署,，则画面迁移至授权书签署画面
                    //2.不需要签署，则提交审核，审核通过后，画面迁移至 提交成功 画面
                    break;
            }
        }
    }
}
