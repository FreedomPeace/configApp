package com.pccc.shoudan.business.teyue.logic.main.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

public class POSItemViewModel extends MainItemBaseViewModel {

    public POSItemViewModel(Context context) {
        super(context);
    }
    /**openApi接入*/
    public ObservableBoolean openApiEnter = new ObservableBoolean();
    /**
     * 刷卡预授权交易
     */
    public ObservableBoolean authorityTransaction = new ObservableBoolean();
    /**
     * 自助转账
     */
    public ObservableBoolean selfServiceTransfer = new ObservableBoolean();
    /**
     * 刷脸支付
     */
    public ObservableBoolean facePay = new ObservableBoolean();
    /**
     * 免密免签
     */
    public ObservableBoolean noSecretNoSign = new ObservableBoolean();
    /**终端数量总数*/
    public ObservableInt posNum = new ObservableInt();
}

