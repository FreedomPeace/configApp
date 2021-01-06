package com.pccc.shoudan.business.information_change;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pccc.shoudan.business.information_change.status.StatusAdjustActivity;
import com.pccc.shoudan.business.teyue.logic.main.TeYueActivityMain;
import com.techown.merchant.R;
import com.techown.merchant.databinding.ItemMerchantChangeBinding;

import java.util.ArrayList;
import java.util.List;

public class InformationChangeListAdapter extends Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<MerchantInformation> data = new ArrayList<>();

    public List<MerchantInformation> getData() {
        return data;
    }

    public void setData(List<MerchantInformation> data) {
        this.data = data;
    }

    public InformationChangeListAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMerchantChangeBinding binding = ItemMerchantChangeBinding.inflate(
                layoutInflater, viewGroup, false);
        ItemInformationViewModel listViewModel = new ItemInformationViewModel();
        binding.setViewmodel(listViewModel);
        View root = binding.getRoot();
        root.setTag(listViewModel);
        return new MerchantListVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        MerchantInformation merchantInformation = data.get(i);
        View itemView = viewHolder.itemView;
        ItemInformationViewModel itemViewModel = (ItemInformationViewModel)
                itemView.getTag();
        itemViewModel.merchantInformationObservableField.set(merchantInformation);
        Resources resources = itemView.getContext().getResources();
        if (i % 2 == 0) {
            itemView.setBackgroundColor(resources.getColor(R.color.item_bg));
        } else {
            itemView.setBackgroundColor(resources.getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replaceData(List<MerchantInformation> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public static class MerchantListVH extends RecyclerView.ViewHolder {

        public MerchantListVH(@NonNull final View itemView) {
            super(itemView);
            final Context viewContext = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu();
                }

                private void popupMenu() {
                    PopupMenu popupMenu = new PopupMenu(viewContext, itemView.findViewById(R.id.business_type)
                            , Gravity.FILL);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_information_change, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            switch (itemId) {
                                case R.id.merchant_info_change:
                                    viewContext.startActivity(new Intent(viewContext, TeYueActivityMain.class));
                                    break;
                                case R.id.teyue_manage:
                                    viewContext.startActivity(new Intent(viewContext, TeYueActivityMain.class));
                                    break;
                                case R.id.tehui_manage:
                                    viewContext.startActivity(new Intent(viewContext, TeYueActivityMain.class));
                                    break;
                                case R.id.status_change:
                                    viewContext.startActivity(new Intent(viewContext, StatusAdjustActivity.class));
                                    break;
                                case R.id.merchant__detail:
                                    viewContext.startActivity(new Intent(viewContext, TeYueActivityMain.class));
                                    break;
                            }

                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}
