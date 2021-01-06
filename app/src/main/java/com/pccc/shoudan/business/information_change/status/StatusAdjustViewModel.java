package com.pccc.shoudan.business.information_change.status;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.widget.RadioGroup;

import com.techown.merchant.R;

public class StatusAdjustViewModel extends BaseObservable {
    /**调整分类*/
    public ObservableField<Classification> adjustClassification = new ObservableField<>();
    /**调整原因*/
    public ObservableField<String> adjustReason = new ObservableField<>();

    public ObservableField<String> remark = new ObservableField<>();

    public enum Classification {
        Normal,Deactivate,Logout
    }
    public void submit(){

    }
    public void back(){

    }
    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            StatusAdjustViewModel.Classification classification = null;
            switch (checkedId) {
                case R.id.radioButton1:
                    classification = StatusAdjustViewModel.Classification.Normal;
                    break;
                case R.id.radioButton2:
                    classification = StatusAdjustViewModel.Classification.Deactivate;
                    break;
                case R.id.radioButton3:
                    classification = StatusAdjustViewModel.Classification.Logout;
                    break;
            }
            adjustClassification.set(classification);
        }
    };
}
