package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class UnionMerchantViewModel extends TeYueItemViewModel {
    public ObservableField<String> unionMerchant =new ObservableField<>();

    public UnionMerchantViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
