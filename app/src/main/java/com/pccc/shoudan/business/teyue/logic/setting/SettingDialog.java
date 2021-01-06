package com.pccc.shoudan.business.teyue.logic.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.library.adapter.BGARecyclerViewAdapter;
import com.pccc.shoudan.business.teyue.config.BusinessNameRelativeConfig;
import com.pccc.shoudan.business.teyue.config.ItemViewConstant;
import com.pccc.shoudan.business.teyue.logic.BaseDialog;
import com.pccc.shoudan.business.teyue.logic.TeYueParamModel;
import com.pccc.shoudan.business.teyue.logic.detail.SettingDetailDialog;
import com.pccc.shoudan.business.teyue.logic.main.FinishAddCallback;
import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingDialog extends BaseDialog {
    private BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness;
    private String parentBusinessName;
    private FinishAddCallback finishAddCallback;

    public BusinessNameRelativeConfig.ParentRelativeSonBusiness getParentRelativeSonBusiness() {
        return parentRelativeSonBusiness;
    }

    public void setParentRelativeSonBusiness(BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness) {
        this.parentRelativeSonBusiness = parentRelativeSonBusiness;
    }

    public SettingDialog(@NonNull Context context, FinishAddCallback finishAddCallback) {
        super(context);
        this.finishAddCallback = finishAddCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_teyue_setting);
        RecyclerView recyclerView = findViewById(R.id.add_ty_container);
        TextView businessName = findViewById(R.id.tv_business_name);
        parentBusinessName = getParentRelativeSonBusiness().getParentBusinessName();
        businessName.setText(parentBusinessName);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TeYueSettingAdapter teYueSettingAdapter = new
                TeYueSettingAdapter(recyclerView,
                R.layout.item_setting_check);
        final List<SonBusinessViewModel> sonBusinessViewModelList = asSonBusinessViewModelList();
        teYueSettingAdapter.setData(sonBusinessViewModelList);
        recyclerView.setAdapter(teYueSettingAdapter);

        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasCheckedBusiness = false;
                for (SonBusinessViewModel sonBusinessViewModel : sonBusinessViewModelList) {
                    hasCheckedBusiness = sonBusinessViewModel.isCheckedBusiness.get();
                    if (hasCheckedBusiness) {
                        break;
                    }
                }
                if (hasCheckedBusiness) {
                    finishAddCallback.onBusinessAddFinishCall(parentBusinessName, sonBusinessViewModelList);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "必须勾选一个业务", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<SonBusinessViewModel> asSonBusinessViewModelList() {
        List<SonBusinessViewModel> sonBusinessViewModelList = new ArrayList<>();
        String[] sonItems = getParentRelativeSonBusiness().getSonItems();
        for (String sonItem : sonItems) {
            final SonBusinessViewModel sonBusinessViewModel =
                    TeYueParamModel.getSonBusinessBean(parentBusinessName, sonItem);
            sonBusinessViewModelList.add(sonBusinessViewModel);
        }
        return sonBusinessViewModelList;
    }

    public class TeYueSettingAdapter extends BGARecyclerViewAdapter<SonBusinessViewModel> {

        public TeYueSettingAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
            super(recyclerView, defaultItemLayoutId);
        }

        @Override
        protected void fillData(com.bank.library.adapter.BGAViewHolderHelper helper,
                                final int position, final SonBusinessViewModel model) {
            final String name = model.sonBusinessName.get();
            helper.getTextView(R.id.teyue_busniess_name).setText(name);
            CheckBox cb = helper.getView(R.id.checkBox_pos);
            cb.setChecked(model.isCheckedBusiness.get());
            helper.getView(R.id.busniess_setting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到设置详情界面
                    HashMap<String, ItemViewConstant.item_tag_name[]> sonBusinessRelativeItemTags = getParentRelativeSonBusiness().
                            getSonBusinessRelativeItemTags();
                    ItemViewConstant.item_tag_name[] itemTagNames = sonBusinessRelativeItemTags.get(name);
                    SettingDetailDialog settingDetailDialog = new SettingDetailDialog(
                            getContext(),
                            itemTagNames,
                            name,
                            parentRelativeSonBusiness.getParentBusinessName());
                    settingDetailDialog.show();
                    settingDetailDialog.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            cb.setChecked(model.isCheckedBusiness.get());
                        }
                    });
                }
            });

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    model.setSelected(isChecked);
                    model.isCheckedBusiness.set(isChecked);
                }
            });
        }
    }
}