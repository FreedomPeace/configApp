package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class BarcodeAlipayViewModel extends TeYueItemViewModel {

    public ObservableField<String> industryStyle = new ObservableField<>();
    public ObservableField<String> alipayMerchant =new ObservableField<>();


    public BarcodeAlipayViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
