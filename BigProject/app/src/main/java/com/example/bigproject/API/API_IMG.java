package com.example.bigproject.API;

public class API_IMG {
    private static String sv_url ="https://leequadapro.000webhostapp.com/Hinhanh/";
    public static Service getService(){
        return APIRetrofit.getClient(sv_url).create(Service.class);
    }
}
