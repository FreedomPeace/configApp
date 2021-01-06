package com.pccc.shoudan.business.teyue.logic.setting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

public class SettingViewModel {
    public ObservableList<SonBusinessViewModel> settingList = new ObservableArrayList<>();
    public ObservableField<String> parentBusinessName = new ObservableField<>();
    public void onExit() {

    }
    public void onSubmit() {

    }
}
