package com.pccc.shoudan.business.teyue.logic.add;

import org.json.JSONObject;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RateTemplateApi {
    @POST("proxy.do")
    Flowable<String> getData(@Body JSONObject b, @Header("wms-message") String message);
}