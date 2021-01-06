package com.pccc.shoudan.business.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.pccc.shoudan.business.information_change.InformationChangeListAdapter;
import com.pccc.shoudan.business.information_change.MerchantInformation;
import com.pccc.shoudan.business.teyue.logic.detail.SettingDetailDialog;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TeYueItemViewModel;
import com.pccc.shoudan.business.teyue.logic.main.TeYueActivityMain;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.MainItemBaseViewModel;

import java.util.List;

public class ListBinding {
    @BindingAdapter("items")
    public static void  setMerchantItems(RecyclerView recyclerView, List<MerchantInformation> items) {
        InformationChangeListAdapter adapter = (InformationChangeListAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }
    @BindingAdapter("items")
    public static void  setDetailItems(RecyclerView recyclerView, List<TeYueItemViewModel> items) {
        SettingDetailDialog.BusinessSettingDetailAdapter adapter = (SettingDetailDialog.BusinessSettingDetailAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.setData(items);
        }
    }
    @BindingAdapter("items")
    public static void  setSettingItems(RecyclerView recyclerView, List<MainItemBaseViewModel>  items) {
        TeYueActivityMain.MainAdapter adapter = (TeYueActivityMain.MainAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.setData(items);
        }
    }

}
