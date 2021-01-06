package com.pccc.shoudan.business.teyue.logic.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techown.merchant.R;

public class TeYueBarcodeMachineToolDialog extends Dialog {
    public static final int POS = 0;
    public static final int MDB = 1;
    public static final int BARCODE = 2;

    public TeYueBarcodeMachineToolDialog(@NonNull Context context) {
        this(context, R.style.Theme_AppCompat_Dialog);
    }
    public TeYueBarcodeMachineToolDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_teyue_barcode_manager, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_barcode_machine);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //
        TeYueAdapter teYueAdapter = new TeYueAdapter();
        recyclerView.setAdapter(teYueAdapter);
        setContentView(view);
    }
    class VH extends RecyclerView.ViewHolder {
        public Button manager;
        public VH(@NonNull final View itemView) {
            super(itemView);

        }
    }
    public class TeYueAdapter extends RecyclerView.Adapter<VH> {

        private class TeYueBean {
            private int itemType;

            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            int id = R.layout.dialog_teyue_barcode_manager_item;
//            if (i == POS) {
//                id = R.layout.item_teyue_main_pos;
//            }
//            else if (i == MDB) {
//                id = R.layout.item_teyue_main_mdb;
//            }
//            else if (i == BARCODE) {
//                id = R.layout.item_teyue_main_barcode;
//            }
            View view = getLayoutInflater().inflate(id, viewGroup, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {

        }

        @Override
        public int getItemViewType(int position) {
//            if (position == 0) {
//                return R.layout.item_teyue_main_pos;
//            }
//            else if (position == 1) {
//                return R.layout.item_teyue_main_barcode;
//            } else if (position == 2) {
//                return R.layout.item_teyue_main_mdb;
//            }
            return position;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}