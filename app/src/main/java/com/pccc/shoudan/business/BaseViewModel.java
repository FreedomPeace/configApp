package com.pccc.shoudan.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.IdRes;

import com.pccc.library.dialog.DialogUtil;
import com.pccc.library.dialog.validator.IValidator;

import java.util.Arrays;
import java.util.List;

/**
 * 输入弹框，{@link com.techown.merchant.R.integer#edit_dialog}
 * 日期输入弹框，{@link com.techown.merchant.R.integer#date_dialog}
 * 列表输入弹框，{@link com.techown.merchant.R.integer#list_dialog}
 */
public abstract class BaseViewModel {
    protected Context context;
    /**用于把控相字段是否可以输入*/
    public ObservableBoolean isItemViewOpen = new ObservableBoolean();
    public BaseViewModel(Context context) {
        this.context = context;
        //所有字段默认都可以输入
        isItemViewOpen.set(true);
    }

    /**
     * 不带校验规格
     *
     * @param field 传入的字段
     */
    public void setEditDialog(ObservableField<String> field) {
        if (!isItemViewOpen.get()) {
            return;
        }
        DialogUtil.newEditDialog(context, "请输入", null, field.get(), new IValidator() {
            @Override
            public boolean validate(String value) throws IValidator.ValidationError {
                return true;
            }
        }, field);

    }


    /**
     *
     *列表选择--根据传进来的字典库，查询数据表，得到列表item的值
     * @param field 存储选中的item的值
     * @param strInt 字典值 定义在 文件dict_strings.xml中
     */
    public void setListDialog(ObservableField<String> field,@IdRes int strInt) {
        if (!isItemViewOpen.get()) {
            return;
        }
        String[] strings = {"cc", "dd"};

        @SuppressLint("ResourceType") String string = context.getResources().getString(strInt);
        strings[1] = string;
        DialogUtil.newListDialog(context, "请输入", Arrays.asList(strings), field);
    }

    /**
     * 列表对话框
     * @param field 存储选中的item的值
     * @param list 展示在列表中数据
     */
    public void setListDialog(ObservableField<String> field, List<String> list) {
        if (!isItemViewOpen.get()) {
            return;
        }
        DialogUtil.newListDialog(context, "请输入", list, field);
    }

    public void setDatePicker(ObservableField<String> field) {
        if (!isItemViewOpen.get()) {
            return;
        }
        DialogUtil.newDatePicker(context, field.get(), "请输入", true, field);
    }
}
