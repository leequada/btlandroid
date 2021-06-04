package com.example.bigproject.API;

import com.example.bigproject.Model.Banner;
import com.example.bigproject.Model.Notificationss;
import com.example.bigproject.Model.User;
import com.example.bigproject.Model.Video;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface Service {

    @GET("UserDAO.php")
    Call<List<User>> getDataAllUser();

    @GET("VideoDAO.php")Call<List<Video>> getDataAllVideo();

    @GET("BannerDAO.php")Call<List<Banner>> getBanner();

    @GET("VideoTOP.php")Call<List<Video>> getVideoTOP();

    @FormUrlEncoded
    @POST("ListVideoUser.php")Call<List<Video>> getListVideo(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("RegisterUser.php")
    Call<String> setValueUser(@Field("Account") String account,
                           @Field("passUser") String passwords ,
                              @Field("nameUser") String nameUser);

    @FormUrlEncoded
    @POST("UploadImg.php")Call<String> setValueImg(@Field("ImageUser") String url);

    @Multipart
    @POST("Severtiktok/UploadVideo.php")
    Call<String> uploadVideo(
            @Part MultipartBody.Part video
    );

    @Multipart
    @POST("Severtiktok/UploadProfile.php")
    Call<String> uploadImage(
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("Severtiktok/UploadVideoDB.php")Call<String> uploadVideoS(
            @Field("Title") String title,
            @Field("UrlVideo") String url,
            @Field("Tag") String tag,
            @Field("IdUser") String iduser
    );

    @FormUrlEncoded
    @POST("SearchVideo.php")Call<List<Video>> getVideobySearch(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("Severtiktok/UpdateProfile.php")Call<String> uploadProfile(
            @Field("urimage") String path,
            @Field("name") String name,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Severtiktok/UpDateLike.php")Call<String> UpDateLikes(
            @Field("id") String id,
            @Field("likes") String likes

    );

    @FormUrlEncoded
    @POST("Severtiktok/InsertNotification.php")Call<String> uploadNotification(
            @Field("content") String content,
            @Field("idUser") String idUser,
            @Field("idClient") String idClient
    );


    @FormUrlEncoded
    @POST("getNotificationbyID.php")Call<List<Notificationss>> getNotification(@Field("id") String id);




}
