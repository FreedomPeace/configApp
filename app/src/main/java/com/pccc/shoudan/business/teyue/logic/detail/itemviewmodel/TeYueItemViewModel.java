package com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel;

import android.content.Context;

import com.pccc.shoudan.business.BaseViewModel;
import com.pccc.shoudan.business.teyue.config.ItemViewConstant;

public abstract class TeYueItemViewModel extends BaseViewModel {

    /**
     * item布局对应名称
     * todo 设计bean结构--多item样式设计
     */
    private ItemViewConstant.ItemViewLayoutId itemViewLayoutId;
    private String itemTagName;
    public TeYueItemViewModel(Context context,String itemTagName ) {
        super(context);
        this.itemViewLayoutId = ItemViewConstant.getItemViewLayoutIdByTagName(itemTagName);
        this.itemTagName = itemTagName;
    }

    public String getItemTagName() {
        return itemTagName;
    }

    public ItemViewConstant.ItemViewLayoutId getItemViewLayoutId() {
        return itemViewLayoutId;
    }
}
