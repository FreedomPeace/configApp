package com.pccc.shoudan.business.teyue.logic;

import android.content.Context;

import com.pccc.shoudan.Log;
import com.pccc.shoudan.business.teyue.config.ItemViewConstant;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TeYueItemViewModel;
import com.pccc.shoudan.business.teyue.logic.setting.SonBusinessViewModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeYueParamModel {
    private static HashMap<String, SonBusinessViewModel> data;
    private static Context context;


    public static void destroy() {
        data.clear();
        data=null;
        context = null;
    }
    public static void init(Context context1) {
        context = context1;
        data = new HashMap<>();
    }

    /**
     * @param parentName      父业务名称  例如  POS刷卡,买单吧收银台，条码
     * @param sonBusinessName 子业务名称  例如 POS刷卡中的 AE ， Dinner club
     * @return
     */
    public static SonBusinessViewModel getSonBusinessBean(String parentName, String sonBusinessName) {
        String sonBusinessKey = parentName + sonBusinessName;
        SonBusinessViewModel oldSonBusinessViewModel = data.get(sonBusinessKey);
        if (oldSonBusinessViewModel != null) {
            return oldSonBusinessViewModel;
        }
        SonBusinessViewModel newSonBusinessViewModel = new SonBusinessViewModel();
        newSonBusinessViewModel.sonBusinessName.set(sonBusinessName);
        newSonBusinessViewModel.parentName.set(parentName);
        newSonBusinessViewModel.isCheckedBusiness.set(false);
        data.put(sonBusinessKey, newSonBusinessViewModel);

        return newSonBusinessViewModel;
    }
    /**
     * @param parentName      父业务名称  例如  POS刷卡,买单吧收银台，条码
     * @param sonBusinessName 子业务名称  例如 POS刷卡中的 AE ， Dinner club
     * @return
     */
    public static void removeSonBusinessBean(String parentName, String sonBusinessName) {
        String sonBusinessKey = parentName + sonBusinessName;
        data.remove(sonBusinessKey);
    }

    /**
     * com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel 这个包下所有的viewmodel反射创建实例
     * {@link com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeAlipayViewModel}
     * ....
     * {@link com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.UnionMerchantViewModel}
     * @param parentName 父业务的名字
     * @param sonBusinessName 子业务的名字
     * @param itemTagNames 子业务item的名字
     * @return
     */
    @SuppressWarnings("unchecked")
    public  static List<TeYueItemViewModel> getSonItemViewModel(String parentName, String sonBusinessName,
                                                                ItemViewConstant.item_tag_name[] itemTagNames) {
        SonBusinessViewModel sonBusinessViewModel = getSonBusinessBean(parentName, sonBusinessName);
        if (sonBusinessViewModel.itemBeans.size() > 0) {
            return sonBusinessViewModel.itemBeans;
        }
        List<TeYueItemViewModel> itemBeans = new ArrayList<>();
        for (ItemViewConstant.item_tag_name itemTagName : itemTagNames) {
            TeYueItemViewModel o = null;
            try {
                Class<?> aClass = Class.forName(itemTagName.getBeanClass());
//                 o =aClass.newInstance();
                Constructor<TeYueItemViewModel> constructor =
                        (Constructor<TeYueItemViewModel>) aClass.getConstructor(Context.class,String.class);
                constructor.setAccessible(true);
                o = constructor.newInstance(context,itemTagName.getName());
            } catch (Exception e) {
                e.printStackTrace();
                Log.getInstance().writeLog("反射TeYueItemViewModel的子类实例异常"+e.getMessage());
            }
            if (o!=null) {
                itemBeans.add(o);
            }
        }
        sonBusinessViewModel.itemBeans.addAll(itemBeans);

        return sonBusinessViewModel.itemBeans;
    }

}
