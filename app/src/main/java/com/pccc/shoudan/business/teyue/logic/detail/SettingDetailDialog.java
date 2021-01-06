package com.pccc.shoudan.business.teyue.logic.detail;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pccc.shoudan.business.teyue.config.ItemViewConstant;
import com.pccc.shoudan.business.teyue.logic.BaseDialog;
import com.pccc.shoudan.business.teyue.logic.TeYueParamModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeAlipayViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeUnionViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeWechatViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.DebitCommonViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.MDBStagingViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.MccViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.PosStagingViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TccViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TeYueItemViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.UnionMerchantViewModel;
import com.techown.merchant.databinding.DialogTeyueBusinessSettingDetailBinding;
import com.techown.merchant.databinding.ItemBarcodeAlipayBinding;
import com.techown.merchant.databinding.ItemBarcodeUnionBinding;
import com.techown.merchant.databinding.ItemBarcodeWechatBinding;
import com.techown.merchant.databinding.ItemDebitCommonBinding;
import com.techown.merchant.databinding.ItemMccBinding;
import com.techown.merchant.databinding.ItemMdbStagingBinding;
import com.techown.merchant.databinding.ItemPosStagingBinding;
import com.techown.merchant.databinding.ItemTccBinding;
import com.techown.merchant.databinding.ItemUnionMerchantBinding;

import java.util.ArrayList;
import java.util.List;

import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Barcode_Alipay;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Barcode_Union;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Barcode_Wechat;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Debit_Common;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Mcc;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Mdb_Staging;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Pos_Staging;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Tcc;
import static com.pccc.shoudan.business.teyue.config.ItemViewConstant.ItemViewLayoutId.Union_Merchant;

public class SettingDetailDialog extends BaseDialog {

    private final ItemViewConstant.item_tag_name[] itemTagNames;
    private final String sonBusinessName;
    private final String parentName;
    private LayoutInflater layoutInflate;

    public SettingDetailDialog(@NonNull Context context, ItemViewConstant.item_tag_name[] itemTagNames,
                               String sonBusinessName, String parentName) {
        super(context);
        this.itemTagNames = itemTagNames;
        this.sonBusinessName = sonBusinessName;
        this.parentName = parentName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogTeyueBusinessSettingDetailBinding binding =
                DialogTeyueBusinessSettingDetailBinding.inflate(layoutInflate);
        View contentView = binding.getRoot();
        setContentView(contentView);
        binding.settingDetailContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置 子业务标题
        binding.tvBusinessName.setText(sonBusinessName);
        BusinessSettingDetailAdapter businessSettingDetailAdapter =
                new BusinessSettingDetailAdapter();
        SettingDetailViewModel viewmodel = new SettingDetailViewModel(this,parentName,sonBusinessName);
        binding.setViewmodel(viewmodel);
        List<TeYueItemViewModel> sonItemViewModel = TeYueParamModel.getSonItemViewModel(
                parentName, sonBusinessName, itemTagNames
        );
        viewmodel.itemBeans.addAll(sonItemViewModel);
        binding.settingDetailContainer.setAdapter(businessSettingDetailAdapter);
    }

    public class BusinessSettingDetailAdapter extends
            RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private List<TeYueItemViewModel> data;

        @Override
        public int getItemViewType(int position) {
//          return   GetItemLayoutIdByTagName.getItemViewLayoutIdByTagName()
            return data.get(position).getItemViewLayoutId().getValue();
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
//            View itemView = layoutInflate.inflate(viewType, parent, false);
            RecyclerView.ViewHolder viewHolder;
            ViewDataBinding binding = null;
            if (viewType == Barcode_Alipay.getValue()) {
                 binding =ItemBarcodeAlipayBinding.inflate(
                                layoutInflate, parent, false
                        );
            } else if (viewType == Barcode_Union.getValue()) {
                 binding = ItemBarcodeUnionBinding.inflate(
                                layoutInflate, parent, false
                        );
            } else if (viewType == Barcode_Wechat.getValue()) {
                 binding = ItemBarcodeWechatBinding.inflate(
                                layoutInflate, parent, false
                        );
            }
//            java.lang.RuntimeException: view tag isn't correct on view:null
//        at com.techown.merchant.databinding.ItemMccBinding.bind(ItemMccBinding.java:208)
            else if (viewType == Mcc.getValue()) {
                 binding =ItemMccBinding.inflate(
                                layoutInflate, parent, false
                        );
            }
            else if (viewType == Mdb_Staging.getValue()) {
                binding = ItemMdbStagingBinding.inflate(
                        layoutInflate, parent, false
                );
            }
            else if (viewType == Pos_Staging.getValue()) {
                binding = ItemPosStagingBinding.inflate(
                        layoutInflate, parent, false
                );
            }
            else if (viewType == Tcc.getValue()) {
                binding = ItemTccBinding.inflate(
                        layoutInflate, parent, false
                );
            }
            else if (viewType == Union_Merchant.getValue()) {
                binding = ItemUnionMerchantBinding.inflate(
                        layoutInflate, parent, false
                );
            }
            else if (viewType == Debit_Common.getValue()) {
                binding =ItemDebitCommonBinding.inflate(
                        layoutInflate, parent, false
                );
            }
            itemView = binding.getRoot();
            itemView.setTag(binding);
            viewHolder = new BarcodeAlipayVH(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            TeYueItemViewModel teYueItemViewModel = data.get(i);
            ItemViewConstant.ItemViewLayoutId layoutId = teYueItemViewModel.getItemViewLayoutId();
            switch (layoutId) {
                case Barcode_Alipay:
                    ((ItemBarcodeAlipayBinding) viewHolder.itemView.getTag())
                            .setViewmodel((BarcodeAlipayViewModel) teYueItemViewModel);
                    break;
                case Barcode_Union:
                    ((ItemBarcodeUnionBinding) viewHolder.itemView.getTag())
                            .setViewmodel((BarcodeUnionViewModel) teYueItemViewModel);
                    break;
                case Barcode_Wechat:
                    ((ItemBarcodeWechatBinding) viewHolder.itemView.getTag())
                            .setViewmodel((BarcodeWechatViewModel) teYueItemViewModel);
                    break;
                case Debit_Common:
                    ((ItemDebitCommonBinding) viewHolder.itemView.getTag())
                            .setViewmodel((DebitCommonViewModel) teYueItemViewModel);
                    break;
                case Mcc:
                    ((ItemMccBinding) viewHolder.itemView.getTag())
                            .setViewmodel((MccViewModel) teYueItemViewModel);
                    break;
                case Mdb_Staging:
                    ((ItemMdbStagingBinding) viewHolder.itemView.getTag())
                            .setViewmodel((MDBStagingViewModel) teYueItemViewModel);
                    break;
                case Pos_Staging:
                    ((ItemPosStagingBinding) viewHolder.itemView.getTag())
                            .setViewmodel((PosStagingViewModel) teYueItemViewModel);
                    break;
                case Tcc:
                    ((ItemTccBinding) viewHolder.itemView.getTag())
                            .setViewmodel((TccViewModel) teYueItemViewModel);
                    break;
                case Union_Merchant:
                    ((ItemUnionMerchantBinding) viewHolder.itemView.getTag())
                            .setViewmodel((UnionMerchantViewModel) teYueItemViewModel);
                    break;
            }
        }

        public void setData(List<TeYueItemViewModel> itemBeans) {
            if (itemBeans == null) {
                itemBeans = new ArrayList<>();
            }
            data = itemBeans;
        }

        class BarcodeAlipayVH extends RecyclerView.ViewHolder {

            public BarcodeAlipayVH(@NonNull View itemView) {
                super(itemView);
            }
        }


    }
}