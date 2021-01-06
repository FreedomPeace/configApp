package com.pccc.shoudan.business.teyue.logic.add;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bank.library.https.RetrofitManager;
import com.pccc.shoudan.business.teyue.logic.main.FinishAddCallback;
import com.pccc.shoudan.business.teyue.logic.BaseDialog;
import com.techown.merchant.R;
import com.techown.merchant.utils.CurrentInfo;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddTeYueBusinessDialog extends BaseDialog
{
    private FinishAddCallback callback;
    private Disposable disposable;

    public AddTeYueBusinessDialog(@NonNull Context context) {
        super(context);
        this.callback = (FinishAddCallback) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_teyue_business_add_enter);
        RecyclerView recyclerView = findViewById(R.id.add_ty_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //开通ＰＯＳ卡设置
        AddTeYueAdapter teYueAddBusinessAdapter = new AddTeYueAdapter(recyclerView,
                R.layout.dialog_teyue_business__add_enter_item);
        recyclerView.setAdapter(teYueAddBusinessAdapter);
        teYueAddBusinessAdapter.setFinishAddCallback(callback);
        findViewById(R.id.select_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 费率模板选择
                getRateTemplate();
            }
        });
    }
    private void getRateTemplate(){
        Retrofit retrofit = RetrofitManager.newBuilder(CurrentInfo.identity,
                CurrentInfo.userNum, CurrentInfo.imei)
                .baseUrl(CurrentInfo.uriAPI)
                .build();
        try {
            JSONObject b = new JSONObject();
            b.put("methodKey", "ddsdf");
            disposable = retrofit.create(RateTemplateApi.class).getData(b, "rateTemplate")
                    .subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).subscribe(
                            new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {

                                }
                            }
                    );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposable!=null&&!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}