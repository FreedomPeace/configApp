package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class BarcodeWechatViewModel extends TeYueItemViewModel {
    /**行业类型*/
    public ObservableField<String> industryStyle = new ObservableField<>();
    public ObservableField<String> wechatMerchant = new ObservableField<>();
    public ObservableField<String> channelRate = new ObservableField<>();

    public BarcodeWechatViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
