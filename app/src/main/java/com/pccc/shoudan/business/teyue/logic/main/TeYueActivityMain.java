package com.pccc.shoudan.business.teyue.logic.main;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;
import com.pccc.shoudan.business.teyue.config.BusinessNameRelativeConfig;
import com.pccc.shoudan.business.teyue.config.ItemViewConstant;
import com.pccc.shoudan.business.teyue.logic.TeYueParamModel;
import com.pccc.shoudan.business.teyue.logic.detail.SettingDetailDialog;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.MainItemBaseViewModel;
import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;
import com.techown.merchant.R;
import com.techown.merchant.databinding.ActivityTeyueMainBinding;
import com.techown.merchant.databinding.ItemTeyueMainBarcodeBinding;
import com.techown.merchant.databinding.ItemTeyueMainMdbBinding;
import com.techown.merchant.databinding.ItemTeyueMainPosBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TeYueActivityMain extends AppCompatActivity implements FinishAddCallback {
    private HashMap<String, BusinessNameRelativeConfig.ParentRelativeSonBusiness> mParentBusinesses;
    private MainViewModel viewmodel;

    @Override
    public void onBusinessAddFinishCall(String parentName, List<
            SonBusinessViewModel> sonBusinessViewModelList) {
        viewmodel.onBusinessAddFinishCall(parentName,sonBusinessViewModelList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusinessNameRelativeConfig.destroy();
        TeYueParamModel.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 特约参数
        TeYueParamModel.init(this);
        mParentBusinesses = new LinkedHashMap<>();
        BusinessNameRelativeConfig.init();
        mParentBusinesses.putAll(BusinessNameRelativeConfig.parentBusinesses);
        ActivityTeyueMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_teyue_main);
        viewmodel = new MainViewModel(this);
        binding.setViewmodel(viewmodel);
        //init 特约主页面recycleView
        RecyclerView finishList = binding.teyueFinishList;
        finishList.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter();
        finishList.setAdapter(mainAdapter);
    }

    class BusinessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<SonBusinessViewModel> sonBusinessViewModelList = new ArrayList<>();

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_teyue_main_item, viewGroup, false);
            return new RecyclerView.ViewHolder(view) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            SonBusinessViewModel model = sonBusinessViewModelList.get(i);
            final String sonName = model.sonBusinessName.get();
            final String parentName = model.parentName.get();
            TextView smallBusinessName = viewHolder.itemView.findViewById(R.id.small_business_name);
            smallBusinessName.setText(sonName);
            viewHolder.itemView.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳到对应小业务的设置详情界面
                    BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness =
                            mParentBusinesses.get(parentName);
                    if (parentRelativeSonBusiness == null) {
                        Log.d("跳到对应小业务的设置详情界面", "parentRelativeSonBusiness，找不到对应关系");
                        return;
                    }
                    HashMap<String, ItemViewConstant.item_tag_name[]> sonBusinessRelativeItemTags =
                            parentRelativeSonBusiness.getSonBusinessRelativeItemTags();
                    SettingDetailDialog settingDialog = new SettingDetailDialog(TeYueActivityMain.this,
                            sonBusinessRelativeItemTags.get(sonName)
                            , sonName
                            , parentName
                    );
                    settingDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return sonBusinessViewModelList.size();
        }

        public void setData(List<SonBusinessViewModel> sonBusinessViewModelList) {
            for (SonBusinessViewModel sonBusinessViewModel : sonBusinessViewModelList) {
                ObservableBoolean isOpen = sonBusinessViewModel.isCheckedBusiness;
                if (isOpen.get()) {
                    this.sonBusinessViewModelList.add(sonBusinessViewModel);
                }
            }
        }
    }

    class VH extends RecyclerView.ViewHolder {

        public final RecyclerView businessList;

        public VH(@NonNull final View itemView) {
            super(itemView);
            businessList = itemView.findViewById(R.id.teyue_finish_list);
            businessList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public class MainAdapter extends RecyclerView.Adapter<VH> {
        List<MainItemBaseViewModel> mainItemBaseViewModels;

        public MainAdapter() {
            mainItemBaseViewModels = new ArrayList<>();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ViewDataBinding binding = null;
            LayoutInflater layoutInflater = getLayoutInflater();
            if (i == MainItemBaseViewModel.ItemType.POS.getItemType()) {
                binding = ItemTeyueMainPosBinding.inflate(layoutInflater, viewGroup, false);
            } else if (i == MainItemBaseViewModel.ItemType.MDB.getItemType()) {
                binding = ItemTeyueMainMdbBinding.inflate(layoutInflater, viewGroup, false);
            } else if (i == MainItemBaseViewModel.ItemType.Barcode.getItemType()) {
                binding = ItemTeyueMainBarcodeBinding.inflate(layoutInflater, viewGroup, false);
            }
            VH vh = new VH(binding.getRoot());
            vh.itemView.setTag(binding);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            MainItemBaseViewModel mainItemBaseViewModel = mainItemBaseViewModels.get(i);
            mainItemBaseViewModel.position.set(i);
            mainItemBaseViewModel.setMainViewModel(viewmodel);

            ViewDataBinding binding = (ViewDataBinding) vh.itemView.getTag();
            binding.setVariable(BR.viewmodel, mainItemBaseViewModel);

            //业务列表
            List<SonBusinessViewModel> sonBusinessViewModelList = mainItemBaseViewModel.sonBusinessViewModelList;
            BusinessAdapter adapter = new BusinessAdapter();
            adapter.setData(sonBusinessViewModelList);
            vh.businessList.setAdapter(adapter);
        }

        @Override
        public int getItemViewType(int position) {
            return mainItemBaseViewModels.get(position).itemTypeObservableField.get().getItemType();
        }

        @Override
        public int getItemCount() {
            return mainItemBaseViewModels.size();
        }

        public void setData(List<MainItemBaseViewModel> items) {
            mainItemBaseViewModels = items;
            notifyDataSetChanged();
        }
    }
}