package com.pccc.shoudan.business.tehui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.techown.merchant.R;

import java.util.List;

import com.pccc.shoudan.base.BaseDialog;
import com.pccc.shoudan.business.tehui.dialogpage.SeekMultiSelectDialog;

public class SeekMultiSelectListAdapter extends RecyclerView.Adapter<SeekMultiSelectListAdapter.ViewHolder> {

    private Context mContext;
    private List<Object> mListData;
    private List<Integer> mSelectData;
    private String mType;
    private boolean mIsSearchAll = false;
    private BaseDialog.Callback<Boolean> mCallback;

    public SeekMultiSelectListAdapter(Context context, List<Object> list, String type, List<Integer> selectData,BaseDialog.Callback<Boolean> callback) {
        mContext = context;
        mListData = list;
        mType = type;
        mSelectData = selectData;
        mCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = null;
        if (SeekMultiSelectDialog.TYPE_ADD_STORES.equals(mType)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.seek_multi_select_listview_store_item, viewGroup, false);
        } else if (SeekMultiSelectDialog.TYPE_WEEK.equals(mType)){
            view = LayoutInflater.from(mContext).inflate(R.layout.seek_multi_select_listview_week_item, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Object result = mListData.get(position);
        if (SeekMultiSelectDialog.TYPE_WEEK.equals(mType)) {
            viewHolder.mTvName.setText((String)result);
        } else if (SeekMultiSelectDialog.TYPE_ADD_STORES.equals(mType)) {

        }

        if (mIsSearchAll || mSelectData.contains(position)) {
            viewHolder.mCheckBox.setChecked(true);
        } else {
            viewHolder.mCheckBox.setChecked(false);
        }

        viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更新选中数据源data
                if (viewHolder.mCheckBox.isChecked()) {
                    mSelectData.add(position);
                } else {
                    int index = mSelectData.indexOf(position);
                    if (index != -1) {
                        mSelectData.remove(index);
                    }
                }

                //更新全选状态，如果状态变更，则调用回调通知画面更新
                boolean isCheckAll = false;
                if (mSelectData.size() == mListData.size()) {
                    isCheckAll = true;
                } else {
                    isCheckAll = false;
                }

                if (isCheckAll != mIsSearchAll) {
                    mIsSearchAll = isCheckAll;
                    mCallback.done(mIsSearchAll);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != mListData) {
            return mListData.size();
        }
        return 0;
    }

    /**
     * 数据源变更，更新ListView，并清除已选中内容
     * @param list
     */
    public void updateList(List<Object> list) {
        mSelectData.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置是否全选
     * @param isCheckAll
     */
    public void setIsCheckAll(boolean isCheckAll) {
        mIsSearchAll = isCheckAll;
        if (!isCheckAll) {
            mSelectData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中内容数据源
     * @return
     */
    public List<Integer> getSelectList() {
        return mSelectData;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        public TextView mTvAdrs;
        public CheckBox mCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.seek_multi_select_listview_item_name);
            mTvAdrs = (TextView) itemView.findViewById(R.id.seek_multi_select_listview_item_adrs);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.seek_multi_select_listview_item_checkbox);
        }
    }
}
