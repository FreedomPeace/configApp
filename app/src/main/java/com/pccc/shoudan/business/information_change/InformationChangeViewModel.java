package com.pccc.shoudan.business.information_change;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.pccc.shoudan.business.BaseViewModel;

import java.util.Random;

public class InformationChangeViewModel extends BaseViewModel {
    public InformationChangeViewModel(Context context) {
        super(context);
    }
    //todo Gson 解析
//    public class A {
//        public ObservableField<String> merchantName = new ObservableField<>();
//        public ObservableField<String> businessType = new ObservableField<>();
//        public ObservableField<B> b = new ObservableField<>();
//    }
//
//    public class B {
//        public ObservableField<String> createDate = new ObservableField<>();
//        public ObservableField<String> merchantIdentifier = new ObservableField<>();
//    }

    public ObservableField<String> merchantName = new ObservableField<>();
    public ObservableField<String> merchantIdentifier = new ObservableField<>();
    public ObservableField<String> businessType = new ObservableField<>();
    public ObservableField<String> createDate = new ObservableField<>();
    public ObservableList<MerchantInformation> merchantInformations = new ObservableArrayList<>();

    public void searchChangeList() {
        //todo 调接口查询列表。
        merchantInformations.clear();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int anInt = random.nextInt(20);
            MerchantInformation information = new MerchantInformation();
            information.setMerchantName("商户名称" + i);
            information.setMerchantStatus("status" + anInt);
            information.setMerchantIdentifier("identifier" + anInt);
            information.setBusinessStatus("bus  aa" + anInt);
            information.setBusinessType("type" + anInt);
            information.setCreateDate("2010-12-" + anInt);
            merchantInformations.add(information);
        }
    }
//    public static void main() {
//        final GsonBuilder builder = new GsonBuilder();
//        final TypeAdapterFactory fac = new TypeAdapterFactory() {
//            @Override
//            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
//                Type type = typeToken.getType();
//                if (typeToken.getRawType() != ObservableField.class
//                        || !(type instanceof ParameterizedType)) {
//                    return null;
//                }
//
//                Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
//                TypeAdapter<?> elementAdapter = gson.getAdapter(TypeToken.get(elementType));
//                return (TypeAdapter<T>) newMultisetAdapter(elementAdapter);
//            }
//
//            private <E> TypeAdapter<ObservableField<E>> newMultisetAdapter(
//                    final TypeAdapter<E> elementAdapter) {
//                return new TypeAdapter<ObservableField<E>>() {
//                    public void write(JsonWriter out, ObservableField<E> value) throws IOException {
//                        if (value == null) {
//                            out.nullValue();
//                            return;
//                        }
//                        elementAdapter.write(out, value.get());
//                    }
//
//                    public ObservableField<E> read(JsonReader in) throws IOException {
//                        if (in.peek() == JsonToken.NULL) {
//                            in.nextNull();
//                            return null;
//                        }
//                        if (in.peek() == JsonToken.STRING) {
//                            ObservableField<E> result = new ObservableField<>();
//                            String s = in.nextString();
//                            result.set((E) s);
//                            return result;
//                        }
////                        if (in.peek() == JsonToken.BEGIN_OBJECT) {
////                            ObservableField<E> result = new ObservableField<>();
////                            in.beginObject();
////
////                            result.set((E) s);
////                        }
//                        return null;
//
//                    }
//                };
//            }
//        };
//        builder.registerTypeAdapterFactory(fac);
//        Gson gson = builder.create();
//        A viewModel =
//                gson.fromJson("{\n" +
//                        "\t\"merchantName\":\"tdd\",\n" +
//                        "\t\"businessType\":\"adc\"\n" +
//                        "}", A.class);
////        gson.fromJson("{\n" +
////                "\t\"merchantName\":\"maae\",\n" +
////                "\t\"merchantName\":\"mbbee\",\n" +
////                "\t\"b\":{\n" +
////                "\t\t\"createDate\":\"mccee\",\n" +
////                "\t\t\"merchantIdentifier\":\"meddde\",\n" +
////                "\t\t}\n" +
////                "}", A.class);
//        System.out.println(viewModel.merchantName.get());
//        System.out.println(viewModel.businessType.get());
//        System.out.println(gson.toJson(viewModel));
//        System.out.println(66);
////         RetrofitManager.newBuilder("","","").baseUrl("").build().
//    }
}
