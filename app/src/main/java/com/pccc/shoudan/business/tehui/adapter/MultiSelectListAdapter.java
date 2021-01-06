package com.pccc.shoudan.business.tehui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectListAdapter extends RecyclerView.Adapter<MultiSelectListAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mListData;
    private ArrayList<Integer> mSelectData;

    public MultiSelectListAdapter(Context context, List<String> list, String str) {
        mContext = context;
        mListData = list;
        mSelectData = new ArrayList<>();
        mSelectData.clear();
        if (!TextUtils.isEmpty(str)) {
            for (int i = 0; i < list.size(); i++) {
                if (str.contains(list.get(i))) {
                    mSelectData.add(i);
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.multi_select_listview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.mTvItemName.setText(mListData.get(position));

        if (mSelectData.contains(position)) {
            viewHolder.mIvSelectLogo.setBackgroundColor(Color.BLUE);
        } else {
            viewHolder.mIvSelectLogo.setBackgroundColor(Color.WHITE);
        }

        viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectData.contains(position)) {
                    mSelectData.remove(mSelectData.indexOf(position));
                } else {
                    mSelectData.add(position);
                }
                notifyItemChanged(position);
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

    public List<Integer> getSelectList() {
        return mSelectData;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemName;
        public View mIvSelectLogo;
        public View mItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            mTvItemName = (TextView) itemView.findViewById(R.id.multi_select_listview_item_name);
            mIvSelectLogo = (View) itemView.findViewById(R.id.multi_select_listview_item_selectlogo);
        }
    }
}
