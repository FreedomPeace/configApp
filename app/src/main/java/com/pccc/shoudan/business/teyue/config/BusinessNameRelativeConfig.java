package com.pccc.shoudan.business.teyue.config;

import android.util.Log;

import java.util.HashMap;

public class BusinessNameRelativeConfig {
    /**
     * pos刷卡,买单吧收银台，条码订单支付，三大业务，及其对应的所有子业务，子业务的ItemTag的配置对应
     * {@link ParentRelativeSonBusiness}。
     */
    public static HashMap<String, ParentRelativeSonBusiness> parentBusinesses;
    /**
     * pos刷卡七种子业务的名称 和子业务页面显示的item的对应关系
     */
    private static HashMap<String, ItemViewConstant.item_tag_name[]> posSonBusinessRelativeItemTags;
    /**
     * 买单吧收银台各子业务的名称 和子业务页面显示的item的对应关系
     */
    private static HashMap<String, ItemViewConstant.item_tag_name[]> mdbSonBusinessRelativeItemTags;
    /**
     * 条码，订单支付的各子业务的名称 和子业务页面显示的item的对应关系
     */
    private static HashMap<String, ItemViewConstant.item_tag_name[]> barcodeSonBusinessRelativeItemTags;
    private static boolean hasInit;

    public static void destroy() {
        hasInit = false;
        posSonBusinessRelativeItemTags.clear();
        mdbSonBusinessRelativeItemTags.clear();
        barcodeSonBusinessRelativeItemTags.clear();
        parentBusinesses.clear();

        parentBusinesses = null;
        posSonBusinessRelativeItemTags = null;
        mdbSonBusinessRelativeItemTags = null;
        barcodeSonBusinessRelativeItemTags = null;
        Log.d("BusinessName", "destroy");
    }

    public static void init() {
        if (hasInit) {
            return;
        }
        Log.d("BusinessName", "init");
        if (parentBusinesses == null) {
            parentBusinesses = new HashMap<>();
            posSonBusinessRelativeItemTags = new HashMap<>();
            mdbSonBusinessRelativeItemTags = new HashMap<>();
            barcodeSonBusinessRelativeItemTags = new HashMap<>();
        }
//        parentBusinesses.addAll(Arrays.asList(ParentRelativeSonBusiness.POS,
//                ParentRelativeSonBusiness.MDB,
//                ParentRelativeSonBusiness.Barcode));
        for (String name : posItems) {
            ItemViewConstant.item_tag_name[] itemTagNames = null;
            if (name.contains("银联")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card
                        , ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.abroad_debit_card,
                        ItemViewConstant.item_tag_name.abroad_credit_card,
                        ItemViewConstant.item_tag_name.union_merchant,

                };
            } else if (name.contains("Visa")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.mcc
                };
            } else if (name.contains("MasterCard")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.tcc};
            } else if (name.contains("JCB")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.mcc};
            } else if (name.contains("DINNER")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.mcc};
            } else if (name.contains("AE")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.mcc};
            } else if (name.contains("本行")) {
                itemTagNames = new ItemViewConstant.item_tag_name[]{
                        ItemViewConstant.item_tag_name.territory_debit_card,
                        ItemViewConstant.item_tag_name.territory_credit_card,
                        ItemViewConstant.item_tag_name.pos_staging
                };
            }
            posSonBusinessRelativeItemTags.put(name, itemTagNames);
        }
        for (int i = 0; i < mdbItems.length; i++) {
            ItemViewConstant.item_tag_name[] itemTagNames = null;
            switch (i) {
                //           买单吧
                case 0://本行
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.mdb_staging,

                    };
                    break;
                case 1://银联
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.union_merchant,

                    };
                    break;
                case 2://AE
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card,
                            ItemViewConstant.item_tag_name.union_merchant
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.union_merchant,

                    };
                    break;
            }
            mdbSonBusinessRelativeItemTags.put(mdbItems[i], itemTagNames);

        }
        for (int i = 0; i < barcodeItems.length; i++) {
            ItemViewConstant.item_tag_name[] itemTagNames = null;
            //    二维码。。。。
            switch (i) {
                case 0://银联
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.barcode_union

                    };
                    break;
                case 1://微信
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.barcode_wechat,

                    };
                    break;
                case 2://支付宝
                    itemTagNames = new ItemViewConstant.item_tag_name[]{
                            ItemViewConstant.item_tag_name.territory_debit_card
                            , ItemViewConstant.item_tag_name.territory_credit_card,
                            ItemViewConstant.item_tag_name.barcode_alipay,
                    };
                    break;

            }
            barcodeSonBusinessRelativeItemTags.put(barcodeItems[i], itemTagNames);

        }
        ParentRelativeSonBusiness.POS.setSonBusinessRelativeItemTags(posSonBusinessRelativeItemTags);
        ParentRelativeSonBusiness.MDB.setSonBusinessRelativeItemTags(mdbSonBusinessRelativeItemTags);
        ParentRelativeSonBusiness.Barcode.setSonBusinessRelativeItemTags(barcodeSonBusinessRelativeItemTags);
        parentBusinesses.put(ParentRelativeSonBusiness.POS.parentBusinessName, ParentRelativeSonBusiness.POS);
        parentBusinesses.put(ParentRelativeSonBusiness.MDB.parentBusinessName, ParentRelativeSonBusiness.MDB);
        parentBusinesses.put(ParentRelativeSonBusiness.Barcode.parentBusinessName, ParentRelativeSonBusiness.Barcode);
        hasInit = true;
    }

    private static final String[] businesses = new String[]
            {"POS刷卡", "条码&订单支付", "买单吧收银台"};
    private static final String[] posItems = {
            "银联/网联",
            "Visa",
            "MasterCard",
            "JCB",
            "AE",
            "DINNER CLUB",
            "本行",
    };
    private static final String[] mdbItems = {
            "本行",
            "银联",
            "AE",
    };
    private static String[] barcodeItems = {
            "银联/网联",
            "微信",
            "支付宝",
    };

    /**
     *  pos刷卡,买单吧收银台，条码订单支付，三大业务，及其对应的所有子业务，子业务的ItemTag的枚举
     */
    public enum ParentRelativeSonBusiness {
        POS(businesses[0], posItems, BusinessNameRelativeConfig.posSonBusinessRelativeItemTags),
        Barcode(businesses[1], barcodeItems, barcodeSonBusinessRelativeItemTags),
        MDB(businesses[2], mdbItems, mdbSonBusinessRelativeItemTags);

        private String parentBusinessName;
        private String[] sonItems;
        private HashMap<String, ItemViewConstant.item_tag_name[]> sonBusinessRelativeItemTags;

        public HashMap<String, ItemViewConstant.item_tag_name[]> getSonBusinessRelativeItemTags() {
            return sonBusinessRelativeItemTags;
        }

        public String[] getSonItems() {
            return sonItems;
        }

        public void setSonBusinessRelativeItemTags(HashMap<String, ItemViewConstant.item_tag_name[]> sonBusinessRelativeItemTags) {
            this.sonBusinessRelativeItemTags = sonBusinessRelativeItemTags;
        }

        public String getParentBusinessName() {
            return parentBusinessName;
        }

        /**
         *
         * @param parentBusinessName 大业务名字
         * @param sonItems  大业务对应具体子业务的名称
         * @param sonBusinessRelativeItemTags  子业务关联的item布局的tag
         */
        ParentRelativeSonBusiness(String parentBusinessName, String[] sonItems,
                                  HashMap<String, ItemViewConstant.item_tag_name[]> sonBusinessRelativeItemTags) {
            this.parentBusinessName = parentBusinessName;
            this.sonItems = sonItems;
            this.sonBusinessRelativeItemTags = sonBusinessRelativeItemTags;
        }
    }
}
