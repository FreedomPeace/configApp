package com.pccc.shoudan.business.teyue.logic.main;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.pccc.shoudan.business.teyue.config.BusinessNameRelativeConfig;
import com.pccc.shoudan.business.teyue.logic.add.AddTeYueBusinessDialog;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.BarcodeItemViewModel;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.MDBItemViewModel;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.MainItemBaseViewModel;
import com.pccc.shoudan.business.teyue.logic.main.itemviewmodel.POSItemViewModel;
import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;

import java.util.List;

public  class MainViewModel {

    public ObservableList<MainItemBaseViewModel> mainItemBaseViewModels = new ObservableArrayList<>();
    private Context context;

    public Context getContext() {
        return context;
    }

    public MainViewModel(Context context) {

        this.context = context;
    }

    private AddTeYueBusinessDialog dialog;
    public void onAdd() {
         dialog = new AddTeYueBusinessDialog(context);
         dialog.show();
    }
    public void onBusinessAddFinishCall(String parentName, List<
            SonBusinessViewModel> sonBusinessViewModelList) {
        String pos = BusinessNameRelativeConfig.ParentRelativeSonBusiness.POS.getParentBusinessName();
        String mdb = BusinessNameRelativeConfig.ParentRelativeSonBusiness.MDB.getParentBusinessName();
        String barcode = BusinessNameRelativeConfig.ParentRelativeSonBusiness.Barcode.getParentBusinessName();
        MainItemBaseViewModel.ItemType itemType = null;
        MainItemBaseViewModel mainItemBaseViewModel = null;
        //根据不同的大业务，创建对应的itemViewModel
        if (pos.equals(parentName)) {
            itemType = MainItemBaseViewModel.ItemType.POS;
            mainItemBaseViewModel=  new POSItemViewModel(context);
        } else if (mdb.equals(parentName)) {
            itemType = MainItemBaseViewModel.ItemType.MDB;
            mainItemBaseViewModel=  new MDBItemViewModel(context);
        } else if (barcode.equals(parentName)) {
            itemType = MainItemBaseViewModel.ItemType.Barcode;
            mainItemBaseViewModel = new BarcodeItemViewModel(context);
        } else {
            return;
        }
        BusinessNameRelativeConfig.parentBusinesses.remove(parentName);
        // add
        mainItemBaseViewModel.sonBusinessViewModelList.addAll(sonBusinessViewModelList);
        mainItemBaseViewModel.parentName.set(parentName);
        mainItemBaseViewModel.itemTypeObservableField.set(itemType);
        mainItemBaseViewModels.add(mainItemBaseViewModel);

        if (dialog!=null) {
            dialog.dismiss();
        }
    }
    public void onPre() {

    }

    public void onNext() {
    }

    public void onSave() {
    }
}