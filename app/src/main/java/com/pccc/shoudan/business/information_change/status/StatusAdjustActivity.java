package com.pccc.shoudan.business.information_change.status;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techown.merchant.R;
import com.techown.merchant.databinding.ActivityStatusAdjustBinding;

public class StatusAdjustActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatusAdjustBinding adjustBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_status_adjust);
        StatusAdjustViewModel viewmodel = new StatusAdjustViewModel();
        adjustBinding.setViewmodel(viewmodel);
    }
}