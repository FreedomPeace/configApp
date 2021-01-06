package com.pccc.shoudan.business.teyue.logic.main.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

public class BarcodeItemViewModel extends MainItemBaseViewModel {
    public BarcodeItemViewModel(Context context) {
        super(context);
    }
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
    /**二维码的数量总数*/
    public ObservableInt barcodeNum = new ObservableInt();
}
