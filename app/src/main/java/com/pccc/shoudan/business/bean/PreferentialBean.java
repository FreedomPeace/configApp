package com.pccc.shoudan.business.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description: 信息收集第六步：特惠信息收集
 * ================================================
 */
public class PreferentialBean {

    /**
     * 活动名称
     */
    private String mHuoDongName;

    /**
     * 合同编码
     */
    private String mContractCode;

    /**
     * 开始日期
     */
    private String mStartDate;

    /**
     * 结束日期
     */
    private String mEndDate;

    /**
     * 适用范围
     */
    private String mFanWei;

    /**
     * 优惠同享控制
     */
    private String mYouHuiControl;

    /**
     * 单笔用劵上限
     */
    private String mCeiling;

    /**
     * Pos刷卡对应开关状态
     */
    private boolean mPosSwitch;

    /**
     * Pos刷卡 适用渠道
     */
    private String mPosQuDao;

    /**
     * Pos刷卡 限用卡类型
     */
    private String mPosCardType;

    /**
     * Pos刷卡 限用卡BIN
     */
    private String mPosCardBIN;

    /**
     * Pos刷卡 小票宣传信息
     */
    private String mPosPromoteInfo;

    /**
     * 条码&订单支付 开关状态
     */
    private boolean mPaySwitch;

    /**
     * 条码&订单支付 适用渠道
     */
    private String mPayQuDao;

    /**
     * 在线使用 开关状态
     */
    private boolean mOnlineSwitch;

    /**
     * 在线使用 适用范围
     */
    private String mOnlineFanWei;

    /**
     * 优惠类型集合
     */
    private List<PreferentialTypeInfo> mPFTypeInfoList = new ArrayList<>();

    /**
     * 不适用优惠范围说明
     */
    private String mNotApplyFanWei;

    /**
     * 门店信息集合
     */
    private List<StoresInfo> mStoresInfoList = new ArrayList<>();

    public String getHuoDongName() {
        return mHuoDongName;
    }

    public void setHuoDongName(String mHuoDongName) {
        this.mHuoDongName = mHuoDongName;
    }

    public String getContractCode() {
        return mContractCode;
    }

    public void setContractCode(String mContractCode) {
        this.mContractCode = mContractCode;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getFanWei() {
        return mFanWei;
    }

    public void setFanWei(String mFanWei) {
        this.mFanWei = mFanWei;
    }

    public String getYouHuiControl() {
        return mYouHuiControl;
    }

    public void setYouHuiControl(String mYouHuiControl) {
        this.mYouHuiControl = mYouHuiControl;
    }

    public String getCeiling() {
        return mCeiling;
    }

    public void setCeiling(String mCeiling) {
        this.mCeiling = mCeiling;
    }

    public boolean getPosSwitch() {
        return mPosSwitch;
    }

    public void setPosSwitch(boolean mPosSwitch) {
        this.mPosSwitch = mPosSwitch;
    }

    public String getPosQuDao() {
        return mPosQuDao;
    }

    public void setPosQuDao(String mPosQuDao) {
        this.mPosQuDao = mPosQuDao;
    }

    public String getPosCardType() {
        return mPosCardType;
    }

    public void setPosCardType(String mPosCardType) {
        this.mPosCardType = mPosCardType;
    }

    public String getPosCardBIN() {
        return mPosCardBIN;
    }

    public void setPosCardBIN(String mPosCardBIN) {
        this.mPosCardBIN = mPosCardBIN;
    }

    public String getPosPromoteInfo() {
        return mPosPromoteInfo;
    }

    public void setPosPromoteInfo(String mPosPromoteInfo) {
        this.mPosPromoteInfo = mPosPromoteInfo;
    }

    public boolean getPaySwitch() {
        return mPaySwitch;
    }

    public void setPaySwitch(boolean mPaySwitch) {
        this.mPaySwitch = mPaySwitch;
    }

    public String getPayQuDao() {
        return mPayQuDao;
    }

    public void setPayQuDao(String mPayQuDao) {
        this.mPayQuDao = mPayQuDao;
    }

    public boolean getOnlineSwitch() {
        return mOnlineSwitch;
    }

    public void setOnlineSwitch(boolean mOnlineSwitch) {
        this.mOnlineSwitch = mOnlineSwitch;
    }

    public String getOnlineFanWei() {
        return mOnlineFanWei;
    }

    public void setOnlineFanWei(String mOnlineFanWei) {
        this.mOnlineFanWei = mOnlineFanWei;
    }

    public List<PreferentialTypeInfo> getPFTypeInfoList() {
        return mPFTypeInfoList;
    }

    public void setPFTypeInfoList(List<PreferentialTypeInfo> mPFTypeInfoList) {
        this.mPFTypeInfoList = mPFTypeInfoList;
    }

    public String getNotApplyFanWei() {
        return mNotApplyFanWei;
    }

    public void setNotApplyFanWei(String mNotApplyFanWei) {
        this.mNotApplyFanWei = mNotApplyFanWei;
    }

    public List<StoresInfo> getStoresInfoList() {
        return mStoresInfoList;
    }

    public void setStoresInfoList(List<StoresInfo> mStoresInfoList) {
        this.mStoresInfoList = mStoresInfoList;
    }



    public class PreferentialTypeInfo {

        /**
         * 优惠设置编号
         */
        private int mPFId;

        /**
         *优惠类型
         */
        private String mPFType;

        /**
         * 订单起始金额
         */
        private String mStartSum;

        /**
         * 优惠金额
         */
        private String mPFSum;

        /**
         * 单笔优惠封顶
         */
        private String mSumCaps;

        /**
         * 生效时间1
         */
        private String mTakeEffectTime1;

        /**
         * 失效时间1
         */
        private String mFailureTime1;

        /**
         * 生效时间2
         */
        private String mTakeEffectTime2;

        /**
         * 失效时间2
         */
        private String mFailureTime2;

        /**
         * 周期限用日期
         */
        private String mLimitDate;

        public boolean getIsAllShow() {
            return misAllShow;
        }

        public void setIsAllShow(boolean misAllShow) {
            this.misAllShow = misAllShow;
        }

        private boolean misAllShow;

        public int getPFId() {
            return mPFId;
        }

        public void setPFId(int mPFId) {
            this.mPFId = mPFId;
        }

        public String getPFType() {
            return mPFType;
        }

        public void setPFType(String mPFType) {
            this.mPFType = mPFType;
        }

        public String getStartSum() {
            return mStartSum;
        }

        public void setStartSum(String mStartSum) {
            this.mStartSum = mStartSum;
        }

        public String getPFSum() {
            return mPFSum;
        }

        public void setPFSum(String mPFSum) {
            this.mPFSum = mPFSum;
        }

        public String getSumCaps() {
            return mSumCaps;
        }

        public void setSumCaps(String mSumCaps) {
            this.mSumCaps = mSumCaps;
        }

        public String getTakeEffectTime1() {
            return mTakeEffectTime1;
        }

        public void setTakeEffectTime1(String mTakeEffectTime1) {
            this.mTakeEffectTime1 = mTakeEffectTime1;
        }

        public String getFailureTime1() {
            return mFailureTime1;
        }

        public void setFailureTime1(String mFailureTime1) {
            this.mFailureTime1 = mFailureTime1;
        }

        public String getTakeEffectTime2() {
            return mTakeEffectTime2;
        }

        public void setTakeEffectTime2(String mTakeEffectTime2) {
            this.mTakeEffectTime2 = mTakeEffectTime2;
        }

        public String getFailureTime2() {
            return mFailureTime2;
        }

        public void setFailureTime2(String mFailureTime2) {
            this.mFailureTime2 = mFailureTime2;
        }

        public String getLimitDate() {
            return updateLimitDate(mLimitDate, true);
        }

        public void setLimitDate(String mLimitDate) {
            this.mLimitDate = updateLimitDate(mLimitDate, false);
        }
    }

    public class StoresInfo {
        /**
         * 门店名称
         */
        private String mStoreName;

        /**
         * 行业MCC
         */
        private String mStoreMCC;

        /**
         * 门店城市
         */
        private String mStoreCity;

        /**
         * 开户行账号
         */
        private String mStoreAccount;

        public String getStoreName() {
            return mStoreName;
        }

        public void setStoreName(String mStoreName) {
            this.mStoreName = mStoreName;
        }

        public String getStoreMCC() {
            return mStoreMCC;
        }

        public void setStoreMCC(String mStoreMCC) {
            this.mStoreMCC = mStoreMCC;
        }

        public String getStoreCity() {
            return mStoreCity;
        }

        public void setStoreCity(String mStoreCity) {
            this.mStoreCity = mStoreCity;
        }

        public String getStoreAccount() {
            return mStoreAccount;
        }

        public void setStoreAccount(String mStoreAccount) {
            this.mStoreAccount = mStoreAccount;
        }
    }

    /**
     * 周期限用日期格式转化
     * @param str
     * @param isType
     * @return
     */
    private String updateLimitDate(String str, boolean isType) {
//        String result = "";
//        if (!TextUtils.isEmpty(str)) {
//            if (isType) {
//                char[] chars = str.toCharArray();
//                for (int i = 0; i < chars.length; i++) {
//                    if (chars[i] == '1') {
//                        if ("".equals(result)) {
//                            result = Contant.PF_WEEK[i];
//                        } else {
//                            result = result + "|" + Contant.PF_WEEK[i];
//                        }
//                    }
//                }
//            } else {
//                for (int i = 0; i < Contant.PF_WEEK.length; i++) {
//                    if (str.contains(Contant.PF_WEEK[i])) {
//                        result = result + "1";
//                    } else {
//                        result = result + "0";
//                    }
//                }
//            }
//        }

        return null;
    }
}
