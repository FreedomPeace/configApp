package com.pccc.shoudan.business.tehui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techown.merchant.R;

public class PreferentialInfoListAdapter extends RecyclerView.Adapter<PreferentialInfoListAdapter.ViewHolder> {
    private Context mContext;

    public PreferentialInfoListAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pf_info_listview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除活动
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 更新List
     */
    public void updateListView() {
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvType;
        public TextView mTvName;
        public Button mBtnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvType = (TextView) itemView.findViewById(R.id.info_list_item_tv_type);
            mTvName = (TextView) itemView.findViewById(R.id.info_list_item_tv_name);
            mBtnDelete = (Button) itemView.findViewById(R.id.info_list_item_btn_delete);
        }
    }
}
