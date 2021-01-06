package com.pccc.shoudan.business.teyue.logic.detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.pccc.shoudan.business.teyue.logic.TeYueParamModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TeYueItemViewModel;
import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;

public class SettingDetailViewModel {
    private SettingDetailDialog settingDetailDialog;
    private final String parentName;
    private final String sonBusinessName;
    public ObservableList<TeYueItemViewModel> itemBeans = new ObservableArrayList<>();

    public SettingDetailViewModel(SettingDetailDialog settingDetailDialog, String parentName, String sonBusinessName) {
        this.settingDetailDialog = settingDetailDialog;
        this.parentName = parentName;
        this.sonBusinessName = sonBusinessName;
    }

    public void add(){
        SonBusinessViewModel sonBusinessBean = TeYueParamModel.getSonBusinessBean(parentName, sonBusinessName);
        sonBusinessBean.isCheckedBusiness.set(true);
        if (settingDetailDialog!=null) {
            settingDetailDialog.dismiss();
        }
    }

}
