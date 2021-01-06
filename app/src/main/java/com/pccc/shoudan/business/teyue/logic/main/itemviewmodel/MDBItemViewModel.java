package com.pccc.shoudan.business.teyue.logic.main.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.RadioGroup;

import com.techown.merchant.R;

public class MDBItemViewModel extends MainItemBaseViewModel {
    /**openApi接入*/
    public ObservableBoolean openApiEnter = new ObservableBoolean();
    /**
     * 协议长期有效
     */
    public ObservableField<String> protocolLongValidate = new ObservableField<>("1");
    public ObservableField<String> protocolDeadline = new ObservableField<>();
    public ObservableField<String> contractNo = new ObservableField<>();
    public ObservableField<String> mdbTransactionType = new ObservableField<>();
    /**
     * 收银台支付场景
     */
    public ObservableField<String> checkOutPaySituation = new ObservableField<>();
    public ObservableField<String> payMethod = new ObservableField<>();
    public ObservableField<String> isIntegralNoSecret = new ObservableField<>();
    public ObservableField<String> singleIntegralNoSecretLimit = new ObservableField<>();
    public ObservableField<String> isShopDetailDisplay = new ObservableField<>();

    public MDBItemViewModel(Context context) {
        super(context);
    }
    public RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String one = context.getResources().getString(R.string.one);
            String zero = context.getResources().getString(R.string.zero);
            switch (checkedId) {
                case R.id.radioButton1:
                    protocolLongValidate.set(one);
                    break;
                case R.id.radioButton2:
                    protocolLongValidate.set(zero);
                    break;
                case R.id.radioButton3:
                    isIntegralNoSecret.set(one);
                    break;
                case R.id.radioButton4:
                    isIntegralNoSecret.set(zero);
                    break;
                case R.id.radioButton5:
                    isShopDetailDisplay.set(one);
                    break;
                case R.id.radioButton6:
                    isShopDetailDisplay.set(zero);
                    break;
            }
            System.out.println(checkedId);
        }
    };
}
