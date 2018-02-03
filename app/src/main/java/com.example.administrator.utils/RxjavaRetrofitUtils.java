package com.example.administrator.utils;

import com.example.administrator.constants.MyConstants;
import com.example.administrator.constants.ServiceApi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxjavaRetrofitUtils {
    private static Retrofit retrofit1;
    private static Retrofit retrofit2;

    public static ServiceApi RxJavaRetrofit() {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .addConverterFactory(new ToStringConverterFactory())  //封装
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
                    .baseUrl(MyConstants.Service_URL)
                    .build();
        }
        return retrofit2.create(ServiceApi.class);
    }

    public static ServiceApi getApi() {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
                    .baseUrl(MyConstants.Service_URL)
                    .build();
        }
        return retrofit1.create(ServiceApi.class);
    }


    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
    private static class ToStringConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return new Converter<ResponseBody, String>() {
                    @Override
                    public String convert(ResponseBody value) throws IOException {
                        return value.string();
                    }
                };
            }
            return null;
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return new Converter<String, RequestBody>() {
                    @Override
                    public RequestBody convert(String value) throws IOException {
                        return RequestBody.create(MEDIA_TYPE, value);
                    }
                };
            }
            return null;
        }
    }

}
