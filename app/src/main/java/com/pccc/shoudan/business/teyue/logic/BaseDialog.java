package com.pccc.shoudan.business.teyue.logic;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.techown.merchant.R;

public class BaseDialog extends Dialog {


    public BaseDialog(@NonNull Context context) { //Theme.AppCompat.Light.Dialog
        this(context, R.style.Theme_AppCompat_Light_Dialog);
    }
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setCanceledOnTouchOutside(false);
        View view = findViewById(R.id.textView11);
        if (view!=null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}