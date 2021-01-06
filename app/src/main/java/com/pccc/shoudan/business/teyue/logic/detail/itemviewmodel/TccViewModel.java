package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class TccViewModel extends TeYueItemViewModel {
    /**
     * dcc计费方式
     */
     public ObservableField<String> dcc_countFeeMethod = new ObservableField<>();
    /**
     * dcc费率
     */
     public ObservableField<String> dcc_feeRate = new ObservableField<>();
    /**
     * dcc手续费上限
     */
     public ObservableField<String> dcc_handleFreeLimit = new ObservableField<>();
    /**
     * dcc每笔固定金额
     */
     public ObservableField<String> dcc_fixedAmountPerTransaction = new ObservableField<>();
    /**
     * dcc单笔交易限额
     */
     public ObservableField<String> dcc_singleTransactionLimit = new ObservableField<>();

     public ObservableField<String> tccCode = new ObservableField<>();
     public ObservableBoolean supportFido = new ObservableBoolean();

     public ObservableField<String> organizationId = new ObservableField<>();
     public ObservableField<String> organizationEnglish = new ObservableField<>();
     public ObservableField<String> organizationName = new ObservableField<>();

    public TccViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
