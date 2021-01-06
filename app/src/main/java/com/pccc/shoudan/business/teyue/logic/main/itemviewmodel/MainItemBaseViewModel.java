package com.pccc.shoudan.business.teyue.logic.main.itemviewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.pccc.shoudan.business.BaseViewModel;
import com.pccc.shoudan.business.teyue.config.BusinessNameRelativeConfig;
import com.pccc.shoudan.business.teyue.logic.TeYueParamModel;
import com.pccc.shoudan.business.teyue.logic.main.MainViewModel;
import com.pccc.shoudan.business.teyue.logic.main.TeYueBarcodeMachineToolDialog;
import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;

import java.util.List;

import io.reactivex.annotations.NonNull;

public abstract class MainItemBaseViewModel extends BaseViewModel {
    public ObservableField<ItemType> itemTypeObservableField = new ObservableField<>();
    public ObservableField<String> parentName = new ObservableField<>();
    public ObservableInt position = new ObservableInt();
    public ObservableList<SonBusinessViewModel> sonBusinessViewModelList =new ObservableArrayList<>();
    private MainViewModel mainViewModel;

    public MainItemBaseViewModel(Context context) {
        super(context);
    }

    public void setMainViewModel(@NonNull MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void onDelete() {
        //todo 处理删除的分工 是否放在 MainViewModel 处理
        int adapterPosition = position.get();
        MainItemBaseViewModel remove = mainViewModel.mainItemBaseViewModels.remove(adapterPosition);
        add(remove);
        String sonBusinessName = getSonName(remove);
        TeYueParamModel.removeSonBusinessBean(remove.parentName.get(), sonBusinessName);
    }
    /**
     * 当item删除的时候，往业务中添加 ParentRelativeSonBusiness
     *
     * @param mainItemBaseViewModel
     */
    private void add(MainItemBaseViewModel mainItemBaseViewModel) {
        MainItemBaseViewModel.ItemType type = mainItemBaseViewModel.itemTypeObservableField.get();
        BusinessNameRelativeConfig.ParentRelativeSonBusiness parentRelativeSonBusiness = null;
        if (type == MainItemBaseViewModel.ItemType.POS) {
            parentRelativeSonBusiness = BusinessNameRelativeConfig.ParentRelativeSonBusiness.POS;
        } else if (type == MainItemBaseViewModel.ItemType.MDB) {
            parentRelativeSonBusiness = BusinessNameRelativeConfig.ParentRelativeSonBusiness.MDB;
        } else if (type == MainItemBaseViewModel.ItemType.Barcode) {
            parentRelativeSonBusiness = BusinessNameRelativeConfig.ParentRelativeSonBusiness.Barcode;
        }
        String parentBusinessName = parentRelativeSonBusiness.getParentBusinessName();
        BusinessNameRelativeConfig.parentBusinesses.put(parentBusinessName, parentRelativeSonBusiness);
    }

    public void onManage() {
        TeYueBarcodeMachineToolDialog dialog = new TeYueBarcodeMachineToolDialog(
                context);
        dialog.show();
    }
    private String getSonName(MainItemBaseViewModel remove) {
        List<SonBusinessViewModel> sonBusinessViewModelList = remove.sonBusinessViewModelList;
        String name = "";
        if (sonBusinessViewModelList!=null&&sonBusinessViewModelList.size()>0) {
            SonBusinessViewModel sonBusinessViewModel = sonBusinessViewModelList.get(0);
            name = sonBusinessViewModel.sonBusinessName.get();
        }
        return name;
    }

    public enum ItemType {
        POS(0),MDB(1),Barcode(2);

        private int itemType;

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        ItemType(int itemType) {

            this.itemType = itemType;
        }
    }
}