package com.example.mvp.mvp.model.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService<T> {
    //get请求
    @GET
    Observable<ResponseBody> requestData(@Url String url);

    //参数很多的get请求
    @GET
    Observable<ResponseBody> requestData(@Url String url, @QueryMap Map<String, T> params);

    //文件下载
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);


    //没参数的post请求
    @POST
    Observable<ResponseBody> requestFormData(@Url String url);

    //只有请求头的post请求
    @POST
    Observable<ResponseBody> requestFormData(@Url String url, @HeaderMap HashMap<String, T> headers);

    //只有参数的post请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> requestFormData(@Url String url, @FieldMap Map<String, T> params);

    //有请求头并且参数是键值对的post请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> requestFormData(@Url String url, @HeaderMap Map<String, T> headers,
                                             @FieldMap Map<String, T> params);


    //参数是json串的post请求
    @POST
    Observable<ResponseBody> requestFormData(@Url String url, @Body RequestBody requestBody);


    //参数是json串 并且带请求头的post请求
    @POST
    Observable<ResponseBody> requestFormData(@Url String url, @HeaderMap Map<String, T> headers, @Body RequestBody requestBody);


    //单个文件上传
    @POST
    @Multipart
    Observable<ResponseBody> uploadSingleFile(@Url String url, @Part MultipartBody.Part part);


    //多个文件上传方法1
    @POST
    @Multipart
    Observable<ResponseBody> uploadMultipleFiles(@Url String url, @Part MultipartBody.Part... part);


    //多个文件上传方法2
    @POST
    @Multipart
    Observable<ResponseBody> uploadMultipleFiles(@Url String url, @PartMap Map<String, T> filesMap);

    //单个文件+字符串上传
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFile(@Url String url, @Body RequestBody requestBody, @Part MultipartBody.Part part);

    //多个文件+键值对 上传1
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFiles(@Url String url, @Body RequestBody body, @PartMap Map<String, T> filesMap);

    //多个文件+键值对 上传2
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFiles(@Url String url, @Body RequestBody requestBody, @Part MultipartBody.Part... parts);


}
