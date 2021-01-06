package com.pccc.shoudan.business.teyue.logic.main;

import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;

import java.util.List;

public interface FinishAddCallback {
        void onBusinessAddFinishCall(String ParentName, List<
                SonBusinessViewModel> sonBusinessViewModelList);
}
