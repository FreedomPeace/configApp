package com.pccc.shoudan.business.teyue.logic.setting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TeYueItemViewModel;

public  class SonBusinessViewModel {
    /**
     * 是否开通子业务
     */
    public ObservableBoolean isCheckedBusiness = new ObservableBoolean();
    /**
     * 子业务的item显示名字
     */
    public ObservableField<String> sonBusinessName = new ObservableField<>();
    public ObservableField<String> parentName = new ObservableField<>();
    /**
     * 子业务设置Item，比如本行借记卡，本行贷记卡，银联商户等
     */
    public ObservableList<TeYueItemViewModel> itemBeans = new ObservableArrayList<>();
}