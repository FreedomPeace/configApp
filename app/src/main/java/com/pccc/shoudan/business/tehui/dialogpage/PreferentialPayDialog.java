package com.pccc.shoudan.business.tehui.dialogpage;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.techown.merchant.R;

import java.util.ArrayList;
import java.util.List;

import com.pccc.shoudan.base.BaseDialog;
import com.pccc.shoudan.base.JudgeMethods;
import com.pccc.shoudan.business.bean.PreferentialBean;

/**
 * 特惠开通项具体内容页面
 */
public class PreferentialPayDialog extends BaseDialog {
    private MyClickListener mClickListener;
    private MyCheckChangeListener mCheckChangeLintener;
    private JudgeMethods mDateUtils = new JudgeMethods();
    private PreferentialBean mPfBean = new PreferentialBean();

    private Button mBtnComplete;//完成按钮
    private Button mBtnClose;//返回按钮

    //优惠买单信息
    private TextView mTvHouDongNameSelect, mTvHouDongNameStar;//优惠买单信息--活动名称
    private TextView mTvContractCodeSelect;//优惠买单信息--合同编码
    private TextView mTvStartDateSelect, mTvStartDateStar;//优惠买单信息--开始日期
    private TextView mTvEndDateSelect, mTvEndDateStar;//优惠买单信息--结束日期
    private TextView mTvFanWeiSelect, mTvFanWeiStar;//优惠买单信息--适用范围
    private TextView mTvYouHuiControlSelect, mTvYouHuiControlStar;//优惠买单信息--优惠同享控制
    private TextView mTvCeilingSelect, mTvCeilingStar;//优惠买单信息--单笔用劵上限

    //POS刷卡
    private Switch mChannelPosSwitch;//POS刷卡--开关控件
    private LinearLayout mChannelPosLayout;//POS刷卡--打开布局
    private LinearLayout mChannelPosSwitchLayout;//POS刷卡--开关布局
    private TextView mChannelPOSApplyStar;//POS刷卡--适用渠道标示
    private TextView mChannelPOSApplySelectTv;//POS刷卡--适用渠道tv
    private Button mChannelPOSApplySelectBtn;//POS刷卡--适用渠道btn
    private TextView mChannelPOSCardTypeTv;//POS刷卡--限用卡类型
    private TextView mChannelPOSCardBINTv;//POS刷卡--限用卡BIN
    private TextView mChannelPOSPublicityInfoTv;//POS刷卡--小票宣传信息

    //条码&预定支付
    private Switch mChannelZhiFuSwitch;//条码&预定支付--开关控件
    private LinearLayout mChannelZhiFuLayout;//条码&预定支付--打开布局
    private LinearLayout mChannelZhiFuSwitchLayout;//条码&预定支付--开关布局
    private TextView mChannelZhiFuApplyStar;//条码&预定支付--适用渠道标示
    private TextView mChannelZhiFuApplySelectTv;//条码&预定支付--适用渠道tv
    private Button mChannelZhiFuApplySelectBtn;//条码&预定支付--适用渠道btn

    //在线使用
    private Switch mChannelOnlineSwitch;//在线使用--开关控件
    private LinearLayout mChannelOnlineLayout;//在线使用--打开布局
    private LinearLayout mChannelOnlineSwitchLayout;//在线使用--开关布局
    private TextView mChannelOnlineApplyStar;//在线使用--适用渠道标示
    private TextView mChannelOnlineApplySelectTv;//在线使用--适用渠道tv
    private Button mChannelOnlineApplySelectBtn;//在线使用--适用渠道btn

    //优惠设置
    private LinearLayout mChannelSetNoInfoLayout;//优惠设置--无信息时显示
    private TextView mChannelSetNoInfoSelect;
    private Button mChannelSetAddBtn;//优惠设置--添加按钮
    private RecyclerView mChannelSetListView;//优惠设置--ListView
    private PfSetAdapter mChannelSetAdpater = new PfSetAdapter();
    private List<PreferentialBean.PreferentialTypeInfo> mSetListData = new ArrayList<>();
    private String[] mWeekList = new String[] {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    //不适用优惠范围说明
    private TextView mChannelNotApplyTv;//不适用优惠范围说明tv
    private Button mChannelNotApplySelectBtn;//不适用优惠范围说明btn

    //门店信息
    private TextView mStoresHintTv;//门店信息--无信息提示
    private Button mStoresAddBtn;//门店信息--添加按钮
    private LinearLayout mStoresInfoLayout;//门店信息--展示列表标题
    private RecyclerView mStoresInfoListView;//门店信息--展示列表
    private StoreListAdapter mStoresInfoAdapter = new StoreListAdapter();

    //弹出框对象
    private MultiSelectDialog mMultiSelectDialog;
    private SeekMultiSelectDialog mSeekMultiSelectDialog;

    /**
     * 初始化页面控件
     * @return
     */
    public View initView() {
        mClickListener = new MyClickListener();
        mCheckChangeLintener = new MyCheckChangeListener();
        mMultiSelectDialog = new MultiSelectDialog();
        mSeekMultiSelectDialog = new SeekMultiSelectDialog();

        LayoutInflater factory = LayoutInflater.from(getContext());
        View dialogView = (View) factory.inflate(R.layout.dialog_preferential_pay, null);

        mBtnComplete = (Button) dialogView.findViewById(R.id.complete_btn);
        mBtnClose = (Button) dialogView.findViewById(R.id.item_btn_close);

        //优惠买单信息--活动名称
        mTvHouDongNameSelect = (TextView) dialogView.findViewById(R.id.pf_info_huodong_name_select);
        mTvHouDongNameStar = (TextView) dialogView.findViewById(R.id.pf_info_huodong_name_star);

        //优惠买单信息--合同编码
        mTvContractCodeSelect = (TextView) dialogView.findViewById(R.id.pf_info_contractcode_select);

        //优惠买单信息--开始日期
        mTvStartDateSelect = (TextView) dialogView.findViewById(R.id.pf_info_startdate_select);
        mTvStartDateStar = (TextView) dialogView.findViewById(R.id.pf_info_startdate_star);

        //优惠买单信息--结束日期
        mTvEndDateSelect = (TextView) dialogView.findViewById(R.id.pf_info_enddate_select);
        mTvEndDateStar = (TextView) dialogView.findViewById(R.id.pf_info_enddate_star);

        //优惠买单信息--适用范围
        mTvFanWeiSelect = (TextView) dialogView.findViewById(R.id.pf_info_fanwei_select);
        mTvFanWeiStar = (TextView) dialogView.findViewById(R.id.pf_info_fanwei_star);

        //优惠买单信息--优惠同享控制
        mTvYouHuiControlSelect = (TextView) dialogView.findViewById(R.id.pf_info_youhui_control_select);
        mTvYouHuiControlStar = (TextView) dialogView.findViewById(R.id.pf_info_youhui_control_star);

        //优惠买单信息--单笔用卷上限
        mTvCeilingSelect = (TextView) dialogView.findViewById(R.id.pf_info_ceiling_select);
        mTvCeilingStar = (TextView) dialogView.findViewById(R.id.pf_info_ceiling_star);

        //pos
        mChannelPosSwitch = (Switch) dialogView.findViewById(R.id.pf_channel_pos_switch);
        mChannelPosSwitchLayout = (LinearLayout) dialogView.findViewById(R.id.channel_pos_switch_layout);
        mChannelPosLayout = (LinearLayout) dialogView.findViewById(R.id.channel_pos_layout);
        mChannelPOSApplyStar = (TextView) dialogView.findViewById(R.id.pf_channel_pos_apply_star);
        mChannelPOSApplySelectTv = (TextView) dialogView.findViewById(R.id.pf_channel_pos_apply_select_tv);
        mChannelPOSApplySelectBtn = (Button) dialogView.findViewById(R.id.pf_channel_pos_apply_select_btn);
        mChannelPOSCardTypeTv = (TextView) dialogView.findViewById(R.id.pf_channel_pos_cardtype_tv);
        mChannelPOSCardBINTv = (TextView) dialogView.findViewById(R.id.pf_channel_pos_cardbin_tv);
        mChannelPOSPublicityInfoTv = (TextView) dialogView.findViewById(R.id.pf_channel_pos_promoteinfo_tv);

        //条码&订单支付
        mChannelZhiFuSwitch = (Switch) dialogView.findViewById(R.id.pf_channel_zhifu_switch);
        mChannelZhiFuSwitchLayout = (LinearLayout) dialogView.findViewById(R.id.channel_zhifu_switch_layout);
        mChannelZhiFuLayout = (LinearLayout) dialogView.findViewById(R.id.channel_zhifu_layout);
        mChannelZhiFuApplyStar = (TextView) dialogView.findViewById(R.id.pf_channel_zhifu_apply_star);
        mChannelZhiFuApplySelectTv = (TextView) dialogView.findViewById(R.id.pf_channel_zhifu_apply_select_tv);
        mChannelZhiFuApplySelectBtn = (Button) dialogView.findViewById(R.id.pf_channel_zhifu_apply_select_btn);

        //在线使用
        mChannelOnlineSwitch = (Switch) dialogView.findViewById(R.id.pf_channel_online_switch);
        mChannelOnlineSwitchLayout = (LinearLayout) dialogView.findViewById(R.id.channel_online_switch_layout);
        mChannelOnlineLayout = (LinearLayout) dialogView.findViewById(R.id.channel_online_layout);
        mChannelOnlineApplyStar = (TextView) dialogView.findViewById(R.id.pf_channel_online_apply_star);
        mChannelOnlineApplySelectTv = (TextView) dialogView.findViewById(R.id.pf_channel_online_apply_select_tv);
        mChannelOnlineApplySelectBtn = (Button) dialogView.findViewById(R.id.pf_channel_online_apply_select_btn);

        //优惠设置
        mChannelSetNoInfoLayout = (LinearLayout) dialogView.findViewById(R.id.pf_channel_set_noinfo_layout);
        mChannelSetNoInfoSelect = (TextView) dialogView.findViewById(R.id.pf_channel_set_noinfo_select);
        mChannelSetAddBtn = (Button) dialogView.findViewById(R.id.pf_channel_youhui_set_add_btn);
        mChannelSetListView = (RecyclerView) dialogView.findViewById(R.id.pf_channel_youhui_set_listview);

        //不适用范围说明
        mChannelNotApplyTv = (TextView) dialogView.findViewById(R.id.pf_channel_bushiyong_tv);
        mChannelNotApplySelectBtn = (Button) dialogView.findViewById(R.id.pf_channel_bushiyong_btn);

        //门店信息
        mStoresHintTv = (TextView) dialogView.findViewById(R.id.stores_hint_tv);
        mStoresAddBtn = (Button) dialogView.findViewById(R.id.stores_add_btn);
        mStoresInfoLayout = (LinearLayout) dialogView.findViewById(R.id.stores_info_layout);
        mStoresInfoListView = (RecyclerView) dialogView.findViewById(R.id.stores_info_listview);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mStoresInfoListView.setLayoutManager(manager);
        mStoresInfoListView.setAdapter(mStoresInfoAdapter);

        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        mChannelSetListView.setLayoutManager(manager1);
        mChannelSetListView.setAdapter(mChannelSetAdpater);

        mBtnComplete.setOnClickListener(mClickListener);
        mBtnClose.setOnClickListener(mClickListener);

        //优惠买单信息
        mTvHouDongNameSelect.setOnClickListener(mClickListener);
        mTvContractCodeSelect.setOnClickListener(mClickListener);
        mTvStartDateSelect.setOnClickListener(mClickListener);
        mTvEndDateSelect.setOnClickListener(mClickListener);
        mTvFanWeiSelect.setOnClickListener(mClickListener);
        mTvYouHuiControlSelect.setOnClickListener(mClickListener);
        mTvCeilingSelect.setOnClickListener(mClickListener);

        //pos刷卡
        mChannelPOSApplySelectBtn.setOnClickListener(mClickListener);
        mChannelPOSCardTypeTv.setOnClickListener(mClickListener);
        mChannelPOSCardBINTv.setOnClickListener(mClickListener);
        mChannelPOSPublicityInfoTv.setOnClickListener(mClickListener);

        //条码&订单支付
        mChannelZhiFuApplySelectBtn.setOnClickListener(mClickListener);

        //在线使用
        mChannelOnlineApplySelectBtn.setOnClickListener(mClickListener);

        //优惠设置
        mChannelSetNoInfoSelect.setOnClickListener(mClickListener);
        mChannelSetAddBtn.setOnClickListener(mClickListener);

        //不适用范围说明
        mChannelNotApplySelectBtn.setOnClickListener(mClickListener);

        //门店信息
        mStoresAddBtn.setOnClickListener(mClickListener);

        mChannelPosSwitch.setOnCheckedChangeListener(mCheckChangeLintener);
        mChannelZhiFuSwitch.setOnCheckedChangeListener(mCheckChangeLintener);
        mChannelOnlineSwitch.setOnCheckedChangeListener(mCheckChangeLintener);

        initData();
        return dialogView;
    }

    private void initData() {
        setValue(mPfBean.getHuoDongName(), mTvHouDongNameSelect, mTvHouDongNameStar);//优惠买单信息--活动名称
        mTvContractCodeSelect.setText(mPfBean.getContractCode());//优惠买单信息--合同编码
        setValue(mPfBean.getStartDate(), mTvStartDateSelect, mTvStartDateStar);//优惠买单信息--开始日期
        setValue(mPfBean.getEndDate(), mTvEndDateSelect, mTvEndDateStar);//优惠买单信息--结束日期
        setValue(mPfBean.getFanWei(), mTvFanWeiSelect, mTvFanWeiStar);//优惠买单信息--适用范围
        setValue(mPfBean.getYouHuiControl(), mTvYouHuiControlSelect, mTvYouHuiControlStar);//优惠买单信息--优惠同享控制
        setValue(mPfBean.getCeiling(), mTvCeilingSelect, mTvCeilingStar);//优惠买单信息--单笔用劵上限

        updatePreferentialChannelLayout();
        mChannelPosSwitch.setChecked(mPfBean.getPosSwitch());
        if (mChannelPosSwitch.isChecked()) {
            mChannelPosLayout.setVisibility(View.VISIBLE);

            setValue(mPfBean.getPosQuDao(), mChannelPOSApplySelectTv, mChannelPOSApplyStar);
            mChannelPOSCardTypeTv.setText(mPfBean.getPosCardType());
            mChannelPOSCardBINTv.setText(mPfBean.getPosCardBIN());
            mChannelPOSPublicityInfoTv.setText(mPfBean.getPosPromoteInfo());

        } else {
            mChannelPosLayout.setVisibility(View.GONE);
        }

        mChannelZhiFuSwitch.setChecked(mPfBean.getPaySwitch());
        if (mChannelZhiFuSwitch.isChecked()) {
            mChannelZhiFuLayout.setVisibility(View.VISIBLE);

            setValue(mPfBean.getPayQuDao(), mChannelZhiFuApplySelectTv, mChannelZhiFuApplyStar);
        } else {
            mChannelZhiFuLayout.setVisibility(View.GONE);
        }

        mChannelOnlineSwitch.setChecked(mPfBean.getPaySwitch());
        if (mChannelOnlineSwitch.isChecked()) {
            mChannelOnlineLayout.setVisibility(View.VISIBLE);

            setValue(mPfBean.getOnlineFanWei(), mChannelOnlineApplySelectTv, mChannelOnlineApplyStar);
        } else {
            mChannelOnlineLayout.setVisibility(View.GONE);
        }

        mSetListData.clear();
        mSetListData = mPfBean.getPFTypeInfoList();
        updateYouHuiTypeInfo();
        mChannelSetAdpater.updateList();

        mChannelNotApplyTv.setText(mPfBean.getNotApplyFanWei());

        updateStoresInfoVisibility();
    }



    //*******************************          优惠买单信息          *******************************


    //*******************************            优惠渠道            *******************************
    /**
     * 根据商户特约中 POS刷卡、条码&预定支付、在线使用是否开通，
     * 开通后，特惠页面才可以办理对应业务
     */
    private void updatePreferentialChannelLayout() {
        if (true) {//TODO 特约开通
            mChannelPosSwitchLayout.setVisibility(View.VISIBLE);
        } else {
            mChannelPosSwitchLayout.setVisibility(View.GONE);
        }

        if (true) {
            mChannelZhiFuSwitchLayout.setVisibility(View.VISIBLE);
        } else {
            mChannelZhiFuSwitchLayout.setVisibility(View.GONE);
        }

        if (true) {
            mChannelOnlineSwitchLayout.setVisibility(View.VISIBLE);
        } else {
            mChannelOnlineSwitchLayout.setVisibility(View.GONE);
        }
    }


    //*******************************        优惠渠道--Pos刷卡        *******************************


    //*******************************     优惠渠道--条码&预定支付     *******************************


    //*******************************       优惠渠道--在线使用       *******************************


    //*******************************       优惠渠道--优惠类型       *******************************
    private void updateYouHuiTypeInfo() {
        //更新“添加优惠设置”按钮状态和是否显示初始状态画面
        if (mSetListData.size() == 0 || mSetListData.size() == 3) {
            mChannelSetAddBtn.setVisibility(View.GONE);
            if (mSetListData.size() == 0) {
                mChannelSetNoInfoLayout.setVisibility(View.VISIBLE);
            } else {
                mChannelSetNoInfoLayout.setVisibility(View.GONE);
            }
        } else {
            mChannelSetAddBtn.setVisibility(View.VISIBLE);
            mChannelSetNoInfoLayout.setVisibility(View.GONE);
        }
    }

    //*******************************   优惠渠道--不适用优惠范围说明   *******************************

    //*******************************            门店信息            *******************************

    /**
     * 判断是否有门店信息，更新画面是否显示门店
     */
    private void updateStoresInfoVisibility() {
        List<PreferentialBean.StoresInfo> storesInfoList = mPfBean.getStoresInfoList();
        if (storesInfoList.size() != 0) {//TODO 存在门店信息
            mStoresHintTv.setVisibility(View.GONE);
            mStoresInfoLayout.setVisibility(View.VISIBLE);
        } else {
            mStoresHintTv.setVisibility(View.VISIBLE);
            mStoresInfoLayout.setVisibility(View.GONE);
        }
    }

    //*******************************          数据保存校验          *******************************

    /**
     * 数据保存
     */
    private void saveData() {
        mPfBean.setHuoDongName(mTvHouDongNameSelect.getText().toString());
        mPfBean.setContractCode(mTvContractCodeSelect.getText().toString());
        mPfBean.setStartDate(mTvStartDateSelect.getText().toString());
        mPfBean.setEndDate(mTvEndDateSelect.getText().toString());
        mPfBean.setYouHuiControl(mTvYouHuiControlSelect.getText().toString());
        mPfBean.setFanWei(mTvFanWeiSelect.getText().toString());
        mPfBean.setCeiling(mTvCeilingSelect.getText().toString());

        boolean pos_Checked = mChannelPosSwitch.isChecked();
        mPfBean.setPosSwitch(pos_Checked);
        if (pos_Checked) {
            mPfBean.setPosQuDao(mChannelPOSApplySelectTv.getText().toString());
            mPfBean.setPosCardType(mChannelPOSCardTypeTv.getText().toString());
            mPfBean.setPosCardBIN(mChannelPOSCardBINTv.getText().toString());
            mPfBean.setPosPromoteInfo(mChannelPOSPublicityInfoTv.getText().toString());
        }

        boolean zhiFu_Checked = mChannelZhiFuSwitch.isChecked();
        mPfBean.setPaySwitch(zhiFu_Checked);
        if (zhiFu_Checked) {
            mPfBean.setPayQuDao(mChannelZhiFuApplySelectTv.getText().toString());
        }

        boolean online_Checked = mChannelOnlineSwitch.isChecked();
        mPfBean.setOnlineSwitch(online_Checked);
        if (online_Checked) {
            mPfBean.setOnlineFanWei(mChannelOnlineApplySelectTv.getText().toString());
        }

        mPfBean.setPFTypeInfoList(mSetListData);
        mPfBean.setNotApplyFanWei(mChannelNotApplyTv.getText().toString());
        mPfBean.setStoresInfoList(null);
    }

    /**
     * 数据校验
     * @return 校验结果
     */
    private boolean verify() {
        if (TextUtils.isEmpty(mTvHouDongNameSelect.getText().toString())){
            toast("请输入活动名称！");
            return false;
        }
        if (TextUtils.isEmpty(mTvStartDateSelect.getText().toString())){
            toast("请输入开始日期！");
            return false;
        }
        if (TextUtils.isEmpty(mTvEndDateSelect.getText().toString())){
            toast("请输入结束日期！");
            return false;
        }
        if (TextUtils.isEmpty(mTvYouHuiControlSelect.getText().toString())){
            toast("请输入适用范围！");
            return false;
        }
        if (TextUtils.isEmpty(mTvFanWeiSelect.getText().toString())){
            toast("请输入优惠同享控制！");
            return false;
        }
        if (TextUtils.isEmpty(mTvCeilingSelect.getText().toString())){
            toast("请输入单笔用卷上限！");
            return false;
        }

        boolean pos_Checked = mChannelPosSwitch.isChecked();
        if (pos_Checked) {
            if (TextUtils.isEmpty(mChannelPOSApplySelectTv.getText().toString())){
                toast("请选择POS刷卡的适用渠道！");
                return false;
            }
        }

        boolean zhiFu_Checked = mChannelZhiFuSwitch.isChecked();
        if (zhiFu_Checked) {
            if (TextUtils.isEmpty(mChannelZhiFuApplySelectTv.getText().toString())){
                toast("请选择条码&订单支付的适用渠道！");
                return false;
            }
        }

        boolean online_Checked = mChannelOnlineSwitch.isChecked();
        if (online_Checked) {
            if (TextUtils.isEmpty(mChannelOnlineApplySelectTv.getText().toString())){
                toast("请选择在线使用的适用范围！");
                return false;
            }
        }

        if (mSetListData.size() == 0) {
            toast("请添加对应的优惠设置！");
            return false;
        } else {
            for (int i = 0; i < mSetListData.size(); i++) {
                String pfType = mSetListData.get(i).getPFType();

                if (TextUtils.isEmpty(mSetListData.get(i).getStartSum())){
                    toast("请输入订单起始金额！");
                    return false;
                }
                if (TextUtils.isEmpty(mSetListData.get(i).getSumCaps())){
                    toast("请输入单笔优惠封顶！");
                    return false;
                }

                if (TextUtils.isEmpty(pfType)) {
                    toast("请选择优惠类型！");
                    return false;
                } else {
                    if (("折扣".equals(pfType) && mSetListData.get(i).getIsAllShow()) || "每满减".equals(pfType)) {

                        if (TextUtils.isEmpty(mSetListData.get(i).getTakeEffectTime1())){
                            toast("请输入生效时间！");
                            return false;
                        }
                        if (TextUtils.isEmpty(mSetListData.get(i).getFailureTime1())){
                            toast("请输入失效时间！");
                            return false;
                        }
                        if (TextUtils.isEmpty(mSetListData.get(i).getLimitDate())){
                            toast("请选择周期限用日期！");
                            return false;
                        }
                    }
                }
            }
        }

        if ("指定门店".equals(mTvFanWeiSelect.getText().toString())){
            if (true) {//TODO 无门店信息
                toast("请添加门店信息！");
                return false;
            }
        }
        return true;
    }

    private void toast(String msg){
        Toast.makeText(getContext(), msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 开关变化响应事件类
     */
    private class MyCheckChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
            switch (compoundButton.getId()) {
                case R.id.pf_channel_pos_switch://pos刷卡--开关监控
                    if (isCheck) {
                        mChannelPosLayout.setVisibility(View.VISIBLE);
                    } else {
                        mChannelPosLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.pf_channel_zhifu_switch://支付--开关监控
                    if (isCheck) {
                        mChannelZhiFuLayout.setVisibility(View.VISIBLE);
                    } else {
                        mChannelZhiFuLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.pf_channel_online_switch://在线使用--开关
                    if (isCheck) {
                        mChannelOnlineLayout.setVisibility(View.VISIBLE);
                    } else {
                        mChannelOnlineLayout.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    /**
     * 点击事件响应类
     */
    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.item_btn_close:
//                    Dismiss();
//                    break;
//                case R.id.complete_btn://TODO 校验数据，校验正确后，保存数据，返回特惠主页面
//                    break;
//
//                //优惠买单信息
//                case R.id.pf_info_huodong_name_select://优惠买单信息--活动名称
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入活动名称", "",
//                            mTvHouDongNameSelect.getText().toString(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_LENGTH, 20),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    setValue(data, mTvHouDongNameSelect, mTvHouDongNameStar);
//                                }
//                            });
//                    break;
//                case R.id.pf_info_contractcode_select://优惠买单信息--合同编码
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入合同编码", "",
//                            mTvContractCodeSelect.getText().toString(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_LONG, 16),//TODO 格式未定
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mTvContractCodeSelect.setText(data);
//                                }
//                            });
//                    break;
//                case R.id.pf_info_startdate_select://优惠买单信息--开始日期
//                    DialogUtil.newDatePicker1(
//                            getContext(), mDateUtils.GetCurrentDate1(), "请输入开始日期", true,
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    setValue(data, mTvStartDateSelect, mTvStartDateStar);
//                                }
//                            });
//                    break;
//                case R.id.pf_info_enddate_select://优惠买单信息--结束日期
//                    if (TextUtils.isEmpty(mTvStartDateSelect.getText().toString())) {
//                        toast("请先输入开始时间");
//                    } else {
//                        DialogUtil.newDatePicker1(
//                                getContext(), mTvStartDateSelect.getText().toString(), "请输入结束日期", true,
//                                new DialogUtil.Callback() {
//                                    @Override
//                                    public void done(String data) {
//                                        setValue(data, mTvEndDateSelect, mTvEndDateStar);
//                                    }
//                                });
//                    }
//                    break;
//                case R.id.pf_info_fanwei_select://优惠买单信息--适用范围
//                    DialogUtil.newListDialog(
//                            getContext(), "请选择适用范围", Arrays.asList(new String[]{"商户旗下门店", "指定门店 "}),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    setValue(data, mTvFanWeiSelect, mTvFanWeiStar);
//                                }
//                            });
//                    break;
//                case R.id.pf_info_youhui_control_select://优惠买单信息--优惠同享控制
//                    DialogUtil.newListDialog(
//                            getContext(), "请选择优惠同享控制", Arrays.asList(new String[]{"不予优惠券同享", "不与商户券同享 ", "不与商户其他优惠同享", "不限"}),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    setValue(data, mTvYouHuiControlSelect, mTvYouHuiControlStar);
//                                }
//                            });
//                    break;
//                case R.id.pf_info_ceiling_select://优惠买单信息--单笔用劵上限
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入单笔用劵上限", "",
//                            mTvCeilingSelect.getText().toString(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_INT_MAX, 2, 10),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    setValue(data, mTvCeilingSelect, mTvCeilingStar);
//                                }
//                            });
//                    break;
//
//                //pos刷卡
//                case R.id.pf_channel_pos_apply_select_btn://pos刷卡--渠道选择
//                    mMultiSelectDialog.showDiaglog(getContext(), MultiSelectDialog.TYPE_POS_CHANNEL, mChannelPOSApplySelectTv.getText().toString(), new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            if (!TextUtils.isEmpty(data)) {
//                                setValue(data, mChannelPOSApplySelectTv, mChannelPOSApplyStar);
//                            }
//                        }
//                    });
//                    break;
//                case R.id.pf_channel_pos_cardtype_tv://pos刷卡--限用卡类型
//                    DialogUtil.newListDialog(
//                            getContext(), "请选择限用卡类型", Arrays.asList(new String[]{"交行信用卡2", "交行借记卡3 ", "交行卡4", "不限"}),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mChannelPOSCardTypeTv.setText(data);
//                                }
//                            });
//                    break;
//                case R.id.pf_channel_pos_cardbin_tv://pos刷卡--限用卡BIN
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入限用卡BIN", "",
//                            mChannelPOSCardBINTv.getText().toString(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_LENGTH, 20),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mChannelPOSCardBINTv.setText(data);
//                                }
//                            });
//                    break;
//                case R.id.pf_channel_pos_promoteinfo_tv://pos刷卡--宣传信息
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入限用卡BIN", "",
//                            mChannelPOSPublicityInfoTv.getText().toString(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_LENGTH, 20),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mChannelPOSPublicityInfoTv.setText(data);
//                                }
//                            });
//                    break;
//
//                //条码支付
//                case R.id.pf_channel_zhifu_apply_select_btn://条码&预定支付--适用渠道
//                    mMultiSelectDialog.showDiaglog(getContext(), MultiSelectDialog.TYPE_ZHIFU_CHANNEL, mChannelZhiFuApplySelectTv.getText().toString(), new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            if (!TextUtils.isEmpty(data)) {
//                                setValue(data, mChannelZhiFuApplySelectTv, mChannelZhiFuApplyStar);
//                            }
//                        }
//                    });
//                    break;
//
//                //在线使用
//                case R.id.pf_channel_online_apply_select_btn://在线使用--适用范围
//                    mMultiSelectDialog.showDiaglog(getContext(), MultiSelectDialog.TYPE_ONLINE_CHANNEL, mChannelOnlineApplySelectTv.getText().toString(), new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            setValue(data, mChannelOnlineApplySelectTv, mChannelOnlineApplyStar);
//                        }
//                    });
//                    break;
//
//                //优惠设置
//                case R.id.pf_channel_youhui_set_add_btn://优惠设置--添加优惠设置
//                    mSetListData.add(mPfBean.new PreferentialTypeInfo());
//                    mChannelSetAdpater.updateList();
//                    break;
//                case R.id.pf_channel_set_noinfo_select:
//                    DialogUtil.newListDialog(
//                            getContext(), "请选择优惠类型", Arrays.asList(new String[]{"每满减", "折扣"}),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.clear();
//                                    PreferentialBean.PreferentialTypeInfo info = mPfBean.new PreferentialTypeInfo();
//                                    info.setPFType(data);
//                                    mSetListData.add(info);
//                                    mChannelSetAdpater.updateList();
//                                }
//                            });
//                    break;
//
//                //不适用优惠说明
//                case R.id.pf_channel_bushiyong_btn://不适用优惠说明
//                    mMultiSelectDialog.showDiaglog(getContext(), MultiSelectDialog.TYPE_NOT_APPLY_CHANNEL, mChannelNotApplyTv.getText().toString(), new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            if (!TextUtils.isEmpty(data)) {
//                                mChannelNotApplyTv.setText(data);
//                            }
//                        }
//                    });
//                    break;
//
//                //门店信息
//                case R.id.stores_add_btn://门店信息--添加门店
//                    mSeekMultiSelectDialog.showDiaglog(getContext(), SeekMultiSelectDialog.TYPE_WEEK, "", new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            //TODO 门店信息变更，更新数据
//                            updateStoresInfoVisibility();
//                        }
//                    });
//                    break;
//            }
        }
    }

    /**
     * 优惠设置Adapter
     */
    class PfSetAdapter extends RecyclerView.Adapter<PfSetAdapter.ViewHolder> implements View.OnClickListener {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.pf_set_listview_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            String pfType = mSetListData.get(position).getPFType();
            boolean isAllShowZK = false;

            if ("折扣".equals(pfType)) {
                viewHolder.mTvPfSetYouHuiJinELayout.setVisibility(View.GONE);
                viewHolder.mTvPfSetYouHuiJinESelect.setText("");
                isAllShowZK = getExistZeKou(position);
            } else {
                viewHolder.mTvPfSetYouHuiJinELayout.setVisibility(View.VISIBLE);
                setValue(mSetListData.get(position).getPFSum(), viewHolder.mTvPfSetYouHuiJinESelect, viewHolder.mTvPfSetYouHuiJinEStar);
                isAllShowZK = true;
            }

            if ("折扣".equals(pfType) && !isAllShowZK) {
                viewHolder.mLayoutTime1.setVisibility(View.GONE);
                viewHolder.mLayoutTime2.setVisibility(View.GONE);
                viewHolder.mLayoutDate.setVisibility(View.GONE);
            } else {
                viewHolder.mLayoutTime1.setVisibility(View.VISIBLE);
                viewHolder.mLayoutTime2.setVisibility(View.VISIBLE);
                viewHolder.mLayoutDate.setVisibility(View.VISIBLE);
            }

            viewHolder.mTvPfSetTitle.setText("优惠类型" + (position + 1));
            setValue(mSetListData.get(position).getPFType(), viewHolder.mTvPfSetTypeSelect, viewHolder.mTvPfSetTypeStar);
            setValue(mSetListData.get(position).getStartSum(), viewHolder.mTvPfSetQiShiJinESelect, viewHolder.mTvPfSetQiShiJinEStar);
            setValue(mSetListData.get(position).getSumCaps(), viewHolder.mTvPfSetFengDingSelect, viewHolder.mTvPfSetFengDingStar);
            setValue(mSetListData.get(position).getTakeEffectTime1(), viewHolder.mTvPfSetSXTime1Select, viewHolder.mTvPfSetSXTime1Star);
            setValue(mSetListData.get(position).getFailureTime1(), viewHolder.mTvPfSetShiXTime1Select, viewHolder.mTvPfSetShiXTime1Star);
            viewHolder.mTvPfSetSXTime2Select.setText(mSetListData.get(position).getTakeEffectTime2());
            viewHolder.mTvPfSetShiXTime2Select.setText(mSetListData.get(position).getFailureTime2());
            setStar(mSetListData.get(position).getLimitDate(), viewHolder.mTvDataStar);


            viewHolder.mTvPfSetTypeSelect.setOnClickListener(this);
            viewHolder.mTvPfSetQiShiJinESelect.setOnClickListener(this);
            viewHolder.mTvPfSetYouHuiJinESelect.setOnClickListener(this);
            viewHolder.mTvPfSetFengDingSelect.setOnClickListener(this);
            viewHolder.mTvPfSetSXTime1Select.setOnClickListener(this);
            viewHolder.mTvPfSetShiXTime1Select.setOnClickListener(this);
            viewHolder.mTvPfSetSXTime2Select.setOnClickListener(this);
            viewHolder.mTvPfSetShiXTime2Select.setOnClickListener(this);
            viewHolder.mBtnPfSetDelete.setOnClickListener(this);
            viewHolder.mBtnDataSelect.setOnClickListener(this);

            viewHolder.mTvPfSetTypeSelect.setTag(position);
            viewHolder.mTvPfSetQiShiJinESelect.setTag(position);
            viewHolder.mTvPfSetYouHuiJinESelect.setTag(position);
            viewHolder.mTvPfSetFengDingSelect.setTag(position);
            viewHolder.mTvPfSetSXTime1Select.setTag(position);
            viewHolder.mTvPfSetShiXTime1Select.setTag(position);
            viewHolder.mTvPfSetSXTime2Select.setTag(position);
            viewHolder.mTvPfSetShiXTime2Select.setTag(position);
            viewHolder.mBtnPfSetDelete.setTag(position);
            viewHolder.mBtnDataSelect.setTag(position);
        }

        @Override
        public int getItemCount() {
            if (null != mSetListData) {
                return mSetListData.size();
            }
            return 0;
        }

        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
//            switch (view.getId()) {
//                case R.id.pf_set_delete_btn:
//                    mSetListData.remove(position);
//                    updateList();
//                    break;
//                case R.id.pf_set_xianyongdate_select_btn:
//                    mSeekMultiSelectDialog.showDiaglog(getContext(), SeekMultiSelectDialog.TYPE_WEEK, mSetListData.get(position).getLimitDate(), new Callback<String>() {
//                        @Override
//                        public void done(String data) {
//                            mSetListData.get(position).setLimitDate(data);
//                            updateList();
//                        }
//                    });
//                    break;
//                case R.id.pf_set_type_select://
//                    DialogUtil.newListDialog(
//                            getContext(), "请选择优惠类型", Arrays.asList(new String[]{"每满减", "折扣"}),
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.get(position).setPFType(data);
//                                    updateList();
//                                }
//                            });
//                    break;
//                case R.id.pf_set_qishijine_select:
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入起始金额", "",
//                            mSetListData.get(position).getStartSum(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_INT, 7),//TODO 格式未定
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.get(position).setStartSum(data);
//                                    updateList();
//                                }
//                            });
//                    break;
//                case R.id.pf_set_youhuijine_select:
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入优惠金额", "",
//                            mSetListData.get(position).getPFSum(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_INT, 7),//TODO 格式未定
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.get(position).setPFSum(data);
//                                    updateList();
//                                }
//                            });
//                    break;
//                case R.id.pf_set_fengding_select:
//                    DialogUtil.newEditDialog(
//                            getContext(), "请输入单笔优惠封顶", "",
//                            mSetListData.get(position).getSumCaps(),
//                            ValidatorUtil.validator(ValidatorUtil.CASE_INT, 7),//TODO 格式未定
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.get(position).setSumCaps(data);
//                                    updateList();
//                                }
//                            });
//                    break;
//                case R.id.pf_set_shengxiaoshijian1_select:
//                    DialogUtil.newTimeDialog(
//                            getContext(), mSetListData.get(position).getTakeEffectTime1(), "请输入生效时间1",
//                            new DialogUtil.Callback() {
//                        @Override
//                        public void done(String data) {
//                            mSetListData.get(position).setTakeEffectTime1(data);
//                            updateList();
//                        }
//                    });
//                    break;
//                case R.id.pf_set_shixiaoshijian1_select:
//                    if (TextUtils.isEmpty(mSetListData.get(position).getTakeEffectTime1())) {
//                        toast("请先输入生效时间");
//                    } else {
//                        DialogUtil.newTimeDialog(
//                                getContext(), mSetListData.get(position).getFailureTime1(), "请输入失效时间1",
//                                new DialogUtil.Callback() {
//                                    @Override
//                                    public void done(String data) {
//                                        mSetListData.get(position).setFailureTime1(data);
//                                        updateList();
//                                    }
//                                });
//                    }
//                    break;
//                case R.id.pf_set_shengxiaoshijian2_select:
//                    DialogUtil.newTimeDialog(
//                            getContext(), mSetListData.get(position).getTakeEffectTime2(), "请输入生效时间2",
//                            new DialogUtil.Callback() {
//                                @Override
//                                public void done(String data) {
//                                    mSetListData.get(position).setTakeEffectTime2(data);
//                                    updateList();
//                                }
//                            });
//                    break;
//                case R.id.pf_set_shixiaoshijian2_select:
//                    if (TextUtils.isEmpty(mSetListData.get(position).getTakeEffectTime2())) {
//                        toast("请先输入生效时间");
//                    } else {
//                        DialogUtil.newTimeDialog(
//                                getContext(), mSetListData.get(position).getFailureTime2(), "请输入失效时间2",
//                                new DialogUtil.Callback() {
//                                    @Override
//                                    public void done(String data) {
//                                        mSetListData.get(position).setFailureTime2(data);
//                                        updateList();
//                                    }
//                                });
//                    }
//                    break;
//            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTvPfSetTitle;
            private Button mBtnPfSetDelete;
            private Button mBtnDataSelect;
            private TextView mTvDataStar;
            private RelativeLayout mLayoutTime1;
            private RelativeLayout mLayoutTime2;
            private RelativeLayout mLayoutDate;

            private TextView mTvPfSetTypeSelect;
            private TextView mTvPfSetTypeStar;

            private TextView mTvPfSetQiShiJinESelect;
            private TextView mTvPfSetQiShiJinEStar;

            private TextView mTvPfSetYouHuiJinESelect;
            private TextView mTvPfSetYouHuiJinEStar;
            private RelativeLayout mTvPfSetYouHuiJinELayout;

            private TextView mTvPfSetFengDingSelect;
            private TextView mTvPfSetFengDingStar;

            private TextView mTvPfSetSXTime1Select;
            private TextView mTvPfSetSXTime1Star;

            private TextView mTvPfSetShiXTime1Select;
            private TextView mTvPfSetShiXTime1Star;

            private TextView mTvPfSetSXTime2Select;
            private TextView mTvPfSetShiXTime2Select;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTvPfSetTitle = (TextView) itemView.findViewById(R.id.pf_set_title_tv);
                mBtnPfSetDelete = (Button) itemView.findViewById(R.id.pf_set_delete_btn);
                mBtnDataSelect = (Button) itemView.findViewById(R.id.pf_set_xianyongdate_select_btn);
                mTvDataStar = (TextView) itemView.findViewById(R.id.pf_set_xianyongriqi_star);
                mLayoutTime1 = (RelativeLayout) itemView.findViewById(R.id.pf_set_time1_layout);
                mLayoutTime2 = (RelativeLayout) itemView.findViewById(R.id.pf_set_time2_layout);
                mLayoutDate = (RelativeLayout) itemView.findViewById(R.id.pf_set_date_layout);

                mTvPfSetTypeSelect = (TextView) itemView.findViewById(R.id.pf_set_type_select);
                mTvPfSetTypeStar = (TextView) itemView.findViewById(R.id.pf_set_type_star);

                mTvPfSetQiShiJinESelect = (TextView) itemView.findViewById(R.id.pf_set_qishijine_select);
                mTvPfSetQiShiJinEStar = (TextView) itemView.findViewById(R.id.pf_set_qishijine_star);

                mTvPfSetYouHuiJinELayout = (RelativeLayout) itemView.findViewById(R.id.pf_set_youhuijine_select_layout);
                mTvPfSetYouHuiJinESelect = (TextView) itemView.findViewById(R.id.pf_set_youhuijine_select);
                mTvPfSetYouHuiJinEStar = (TextView) itemView.findViewById(R.id.pf_set_youhuijine_star);

                mTvPfSetFengDingSelect = (TextView) itemView.findViewById(R.id.pf_set_fengding_select);
                mTvPfSetFengDingStar = (TextView) itemView.findViewById(R.id.pf_set_fengding_star);

                mTvPfSetSXTime1Select = (TextView) itemView.findViewById(R.id.pf_set_shengxiaoshijian1_select);
                mTvPfSetSXTime1Star = (TextView) itemView.findViewById(R.id.pf_set_shengxiaoshijian1_star);

                mTvPfSetShiXTime1Select = (TextView) itemView.findViewById(R.id.pf_set_shixiaoshijian1_select);
                mTvPfSetShiXTime1Star = (TextView) itemView.findViewById(R.id.pf_set_shixiaoshijian1_star);

                mTvPfSetSXTime2Select = (TextView) itemView.findViewById(R.id.pf_set_shengxiaoshijian2_select);

                mTvPfSetShiXTime2Select = (TextView) itemView.findViewById(R.id.pf_set_shixiaoshijian2_select);
            }
        }

        /**
         * 更新ListView
         */
        public void updateList() {
            notifyDataSetChanged();
            updateYouHuiTypeInfo();
        }

        /**
         * 获取当前 折扣 项是否全显示
         * @param position
         * @return
         */
        public boolean getExistZeKou(int position) {
            for (int i = 0; i < mSetListData.size(); i++) {
                if (i != position) {
                    if ("折扣".equals(mSetListData.get(i).getPFType())) {
                        if (mSetListData.get(i).getIsAllShow()){
                            mSetListData.get(position).setIsAllShow(false);
                            return false;
                        }
                    }
                }
            }
            mSetListData.get(position).setIsAllShow(true);
            return true;
        }
    }

    /**
     * 门店信息Adapter
     */
    class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
