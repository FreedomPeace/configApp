package com.pccc.shoudan.business.teyue.config;

import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeAlipayViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeUnionViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.BarcodeWechatViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.DebitCommonViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.MDBStagingViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.MccViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.PosStagingViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.TccViewModel;
import com.pccc.shoudan.business.teyue.logic.detail.itemviewmodel.UnionMerchantViewModel;
import com.techown.merchant.R;

import java.util.HashMap;

/**
 * {@link ItemViewLayoutId} 定义了所有特约子业务需要用到的布局itemId
 * {@link item_tag_name}  定义了所有特约子业务Item布局的Tag名字
 * <br/>
 * {@link item_tag_name}  和  {@link ItemViewLayoutId}  的对应关系
 * 在{@link ItemViewConstant#itemType} itemType 中初始化了。
 * 通过{@link ItemViewConstant#getItemViewLayoutIdByTagName} 方法根据tagName来匹配出布局id
 *
 */
public class ItemViewConstant {
    /**
     * pos刷卡,买单吧收银台，条码订单支付，三大业务对应的所有子业务的布局ID
     */
    public enum ItemViewLayoutId {
        Barcode_Alipay(R.layout.item_barcode_alipay),
        Barcode_Union(R.layout.item_barcode_union),
        Barcode_Wechat(R.layout.item_barcode_wechat),
        Debit_Common(R.layout.item_debit_common),
        Mcc(R.layout.item_mcc),
//        Mdb_Debit(R.layout.item_mdb_debit),
        Mdb_Staging(R.layout.item_mdb_staging),
        Pos_Staging(R.layout.item_pos_staging),
        Tcc(R.layout.item_tcc),
        Union_Merchant(R.layout.item_union_merchant);

        private int value;

        ItemViewLayoutId(int value) {

            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * pos刷卡,买单吧收银台，条码订单支付，三大业务对应的所有子业务的ItemTagName
     */
    public enum item_tag_name {
        territory_debit_card("境内借记卡", DebitCommonViewModel.class.getName()),
        territory_credit_card("境内贷记卡", DebitCommonViewModel.class.getName()),
        abroad_debit_card("境外借记卡", DebitCommonViewModel.class.getName()),
        abroad_credit_card("境外贷记卡", DebitCommonViewModel.class.getName()),
        tcc("tcc", TccViewModel.class.getName()),
        mcc("mcc", MccViewModel.class.getName()),
        union_merchant("union_merchant", UnionMerchantViewModel.class.getName()),
        pos_staging("pos_staging", PosStagingViewModel.class.getName()),
        mdb_staging("mdb_staging", MDBStagingViewModel.class.getName()),
//        mbd_debit("mbd_debit", DebitCommonViewModel.class.getName()),
        barcode_wechat("barcode_wechat", BarcodeWechatViewModel.class.getName()),
        barcode_union("barcode_union", BarcodeUnionViewModel.class.getName()),
        barcode_alipay("barcode_alipay", BarcodeAlipayViewModel.class.getName());
        private String name;
        private String beanClass;

        public String getBeanClass() {
            return beanClass;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        item_tag_name(String name, String beanClass) {

            this.name = name;
            this.beanClass = beanClass;
        }
    }
    public static HashMap<String, ItemViewLayoutId> itemType;
    public static ItemViewLayoutId getItemViewLayoutIdByTagName(String tag_name){
        return itemType.get(tag_name);
    }

    static {
        itemType = new HashMap<>();
        itemType.put(item_tag_name.territory_debit_card.getName(), ItemViewLayoutId.Debit_Common);
        itemType.put(item_tag_name.territory_credit_card.getName(), ItemViewLayoutId.Debit_Common);
        itemType.put(item_tag_name.abroad_debit_card.getName(), ItemViewLayoutId.Debit_Common);
        itemType.put(item_tag_name.abroad_credit_card.getName(), ItemViewLayoutId.Debit_Common);
        itemType.put(item_tag_name.tcc.getName(), ItemViewLayoutId.Tcc);
        itemType.put(item_tag_name.mcc.getName(), ItemViewLayoutId.Mcc);
        itemType.put(item_tag_name.union_merchant.getName(), ItemViewLayoutId.Union_Merchant);
        itemType.put(item_tag_name.pos_staging.getName(), ItemViewLayoutId.Pos_Staging);
        itemType.put(item_tag_name.mdb_staging.getName(), ItemViewLayoutId.Mdb_Staging);
//        itemType.put(item_tag_name.mbd_debit.getName(), ItemViewLayoutId.Mdb_Debit);
        itemType.put(item_tag_name.barcode_wechat.getName(), ItemViewLayoutId.Barcode_Wechat);
        itemType.put(item_tag_name.barcode_union.getName(), ItemViewLayoutId.Barcode_Union);
        itemType.put(item_tag_name.barcode_alipay.getName(), ItemViewLayoutId.Barcode_Alipay);
    }

}
