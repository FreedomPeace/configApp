package com.pccc.shoudan.business.teyue.logic.add;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bank.library.adapter.BGARecyclerViewAdapter;
import com.pccc.shoudan.business.teyue.logic.main.FinishAddCallback;
import com.pccc.shoudan.business.teyue.config.BusinessNameRelativeConfig;
import com.pccc.shoudan.business.teyue.logic.setting.SettingDialog;
import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.List;

class AddTeYueAdapter extends BGARecyclerViewAdapter<AddTeYueAdapter.AddBean> {
    private FinishAddCallback finishAddCallback;

    public FinishAddCallback getFinishAddCallback() {
        return finishAddCallback;
    }

    public void setFinishAddCallback(FinishAddCallback finishAddCallback) {
        this.finishAddCallback = finishAddCallback;
    }

    public class AddBean {
        /**
         * 设置选项名称
         */
        private BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness;


        public AddBean(BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness) {
            this.parentRelativeSonBusiness = parentRelativeSonBusiness;
        }

        public BusinessNameRelativeConfig.ParentRelativeSonBusiness getParentRelativeSonBusiness() {
            return parentRelativeSonBusiness;
        }

        public void setParentRelativeSonBusiness(BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness) {
            this.parentRelativeSonBusiness = parentRelativeSonBusiness;
        }
    }

    public AddTeYueAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
        List<AddBean> posData = new ArrayList<>();
        for (BusinessNameRelativeConfig.ParentRelativeSonBusiness business :
                BusinessNameRelativeConfig.parentBusinesses.values()) {
            posData.add(new AddBean(business));
        }
        setData(posData);
    }

    @Override
    protected void fillData(com.bank.library.adapter.BGAViewHolderHelper helper,
                            final int position, final AddBean model) {
        final String businessName = model.getParentRelativeSonBusiness().getParentBusinessName();
        helper.getTextView(R.id.teyue_busniess_name).setText(businessName);
        final View itemView = helper.getRecyclerViewHolder().itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(businessName)) {
                    Context context = itemView.getContext();
                    SettingDialog settingDialog = new SettingDialog(context,getFinishAddCallback());
                   settingDialog.setParentRelativeSonBusiness(model.getParentRelativeSonBusiness());
                    settingDialog.show();
                }

            }
        });
    }
}
