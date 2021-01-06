package com.pccc.shoudan.business.information_change;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.techown.merchant.R;
import com.techown.merchant.databinding.ActivityMerchantChangeListBinding;

public class InformationChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMerchantChangeListBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_merchant_change_list);
        InformationChangeViewModel viewModel = new InformationChangeViewModel(this);
        binding.setViewmodel(viewModel);

        RecyclerView merchantList = binding.merchantList;
        merchantList.setLayoutManager(new LinearLayoutManager(this));
        merchantList.setAdapter(new InformationChangeListAdapter(getLayoutInflater()));

    }
}