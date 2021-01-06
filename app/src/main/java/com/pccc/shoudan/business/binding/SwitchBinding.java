package com.pccc.shoudan.business.binding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SwitchBinding {
    @BindingAdapter("switch")
    public static void  setSwitch(Switch sw, ObservableBoolean isOpen) {
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOpen.set(isChecked);
            }
        });
    }
}
