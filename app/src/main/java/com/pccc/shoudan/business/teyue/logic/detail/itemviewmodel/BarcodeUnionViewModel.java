package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class BarcodeUnionViewModel extends TeYueItemViewModel {
    public ObservableField<String> unionMerchant = new ObservableField<>();
    /**
     * 是否 仅支持本代贷
     */
    public ObservableField<String> isOnlySupportMdb =new ObservableField<>();

    public BarcodeUnionViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
