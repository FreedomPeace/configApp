package com.bank.library.https;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bank.library.https.encrypt.Compress;
import com.bank.library.https.encrypt.DESUtil;
import com.bank.library.https.encrypt.Des;
import com.bank.library.https.encrypt.SecretkeyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class EncryptCompressInterceptor implements Interceptor {
    private String identity;
    private String employeeNum;
    private String iMei;

    public EncryptCompressInterceptor(String identity,
                                      String employeeNum, String iMei) {
        this.identity = identity;
        this.employeeNum = employeeNum;
        this.iMei = iMei;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .header("wms-identity", identity)
                .header("wms-encrypt", "3des")
                .header("wms-compress", "gzip")
                .method(
                        originalRequest.method(),
                        getNewRequestBody(originalRequest.body())
                ).build();


        return getNewResponse(chain, newRequest);

    }

    private Response getNewResponse(Chain chain, Request newRequest) throws IOException {
        Response originalResponse = chain.proceed(newRequest);
        final ResponseBody originalResponseBody = originalResponse.body();
        if (originalResponseBody==null) {
            return originalResponse;
        }
        InputStream in = originalResponseBody.byteStream();
        if ("3des".equals(originalResponse.header("wms-encrypt"))) {
            in = DESUtil.decrypt(in, SecretkeyUtil.getKey(
            employeeNum, iMei));// 员工号和手机imei号
        }

        if ("gzip".equals(originalResponse.header("wms-compress"))) {
            in = new GZIPInputStream(in);
        }

        final InputStream finalIn = in;
        if (finalIn==null) {
            return originalResponse;
        }
        return originalResponse.newBuilder().body(new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                Source source = Okio.source(finalIn);
                BufferedSource buffer = Okio.buffer(source);
                try {
                    String readString = buffer.readString(StandardCharsets.UTF_8);
                    if (TextUtils.isEmpty(readString)) {
                        return buffer;
                    }
                    JSONObject object = new JSONObject(readString);
                    JSONObject demoJson1 = object.optJSONObject("Body");
                    if (demoJson1==null) {
                        return buffer;
                    }
                    String str = demoJson1.toString();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
                    buffer = Okio.buffer(Okio.source(byteArrayInputStream));

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return buffer;
            }
        }).build();
    }

    private RequestBody getNewRequestBody(final RequestBody body) {
        return new RequestBody() {
            @Override public MediaType contentType() {
                return body.contentType();
            }

            @Override public long contentLength() {
                return -1; // We don't know the compressed length in advance!
            }

            @Override public void writeTo(@NonNull BufferedSink sink) throws IOException {
                BufferedSink originalSink = new Buffer();
                body.writeTo(originalSink);
                Buffer buffer = originalSink.buffer();
                String readString = buffer.readString(StandardCharsets.UTF_8);
//                readString = "{'para':[{'name':'pda_pubdict_entry','ver':''},{'name':'pda_pubdic_type','ver':''}]}";
                byte[] bs = readString.getBytes(StandardCharsets.UTF_8);
                try {
                    bs = Compress.compress(bs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bs = Des.EncryptMode(
                        SecretkeyUtil.getKey(employeeNum, iMei),
                        bs);
                sink.write(bs);
                sink.flush();
                sink.close();
            }
        };
    }
}
