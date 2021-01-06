package com.pccc.shoudan.business.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.pccc.shoudan.business.information_change.InformationChangeActivity;
import com.techown.merchant.R;


/**
 * 图片和跳转的页面有一一对应关系
 * 文字和跳转的页面有一一对应关系
 */
public class MerchantMainActivity extends Activity {
    int[] rightMenuId = new int[]{
            R.drawable.main_business_select,
            R.drawable.main_electric_authority_mannage,
            R.drawable.main_info_change,
            R.drawable.main_merchant_create,
            R.drawable.main_pos_mannage,
            R.drawable.main_sheet_manage,
            R.drawable.main_shop_fix,
            R.drawable.main_shop_manange
    };
    String[] leftMenuTitles = new String[]{
            "收单业务 （新）",

    };
    final String[] leftOneMenuTitles = new String[]{
            "收单业务 （新）",
    };
    final String[] leftMenuAllTitles = new String[]{
            "收单业务 （新）",
            "商户开户",
            "信息变更",
            "业务查询",
            "工单查询",
            "门店管理",
            "终端管理",
            "到店维护",
            "电子授权书管理",
            "人脸注册",
            "待处理申请件"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);

        RecyclerView rightMenu = findViewById(R.id.main_right_menu);
        rightMenu.setLayoutManager(new GridLayoutManager(this, 4));
        rightMenu.setAdapter(new RightAdapter());

        RecyclerView leftMenu = findViewById(R.id.main_left_menu);
        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        leftMenu.setAdapter(new LeftAdapter());
    }

    class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.VHTitle> {
        @NonNull
        @Override
        public VHTitle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_merchant_main_left_title, viewGroup, false);
            View view2 = getLayoutInflater().inflate(R.layout.item_merchant_main_left, viewGroup, false);
            if (i == 0) {
                return new VHTitle(view);
            } else {
                return new VHTitle2(view2);
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull VHTitle vhTitle, int i) {
            if (i == 0) {
                vhTitle.openClose.setImageResource(R.drawable.ic_launcher);
                vhTitle.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (leftMenuTitles.length == 1) {
                            leftMenuTitles = leftMenuAllTitles;
                        } else {
                            leftMenuTitles = leftOneMenuTitles;
                        }
                        notifyDataSetChanged();
                    }
                });
            }
            vhTitle.leftMenuTxt.setText(leftMenuTitles[i]);
            //todo 处理点击事件跳转
            vhTitle.getItemViewType();
        }

        @Override
        public int getItemCount() {
            return leftMenuTitles.length;
        }

        class VHTitle extends RecyclerView.ViewHolder {
            TextView leftMenuTxt;
            ImageView openClose;

            public VHTitle(@NonNull View itemView) {
                super(itemView);
                leftMenuTxt = itemView.findViewById(R.id.item_txt);
                openClose = itemView.findViewById(R.id.iv_open_close_direct);
            }
        }

        class VHTitle2 extends VHTitle {
            TextView leftMenuTxt;

            public VHTitle2(@NonNull View itemView) {
                super(itemView);
                leftMenuTxt = itemView.findViewById(R.id.item_txt);
            }
        }
    }

    /**
     * 右边菜单适配器
     */
   public  class RightAdapter extends RecyclerView.Adapter<RightAdapter.VH> {
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_merchant_main_right, viewGroup, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            RequestManager with = Glide.with(vh.itemView.getContext());
            vh.ivRight.setImageResource(rightMenuId[i]);
            //todo 处理点击事件跳转
            vh.ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MerchantMainActivity.this,
                            InformationChangeActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return rightMenuId.length;
        }

        class VH extends RecyclerView.ViewHolder {
            ImageView ivRight;

            public VH(@NonNull View itemView) {
                super(itemView);
                ivRight = itemView.findViewById(R.id.iv_right);
            }
        }
    }
}
