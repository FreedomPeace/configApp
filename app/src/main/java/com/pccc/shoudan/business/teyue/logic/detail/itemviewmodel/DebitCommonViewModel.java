package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class DebitCommonViewModel extends TeYueItemViewModel {
    /**费率*/
    public ObservableField<String> feeRate = new ObservableField<>();
    /**计费方式*/
    public ObservableField<String> countFeeMethod = new ObservableField<>();
    /**手续费上限*/
    public ObservableField<String> handleFreeLimit = new ObservableField<>();
    /**每笔固定金额*/
    public ObservableField<String> fixedAmountPerTransaction = new ObservableField<>();
    /**单笔交易限额*/
    public ObservableField<String> singleTransactionLimit = new ObservableField<>();


    public DebitCommonViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
