package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class MDBStagingViewModel extends TeYueItemViewModel {
    /**分期起始金额*/
    public ObservableField<String> stagingStartAmount=new ObservableField<>();
    /**卡产品组编号*/
    public ObservableField<String> cardProductNum=new ObservableField<>();
    /**分期期数*/
    public ObservableField<String> stagingCount=new ObservableField<>();
    /**费率类型*/
    public ObservableField<String> rateStyle=new ObservableField<>();
    /**费率内容*/
    public ObservableField<String> rateContent=new ObservableField<>();

    /**贴息费率*/
    public ObservableField<String> tiexiRate=new ObservableField<>();

    public MDBStagingViewModel(Context context, String itemTagName) {
        super(context, itemTagName);
    }
}
