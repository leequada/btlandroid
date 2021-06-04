package com.example.bigproject.API;

public class API_inplement {
    private static String sv_url ="https://leequadapro.000webhostapp.com/Severtiktok/";
    public static Service getService(){
        return APIRetrofit.getClient(sv_url).create(Service.class);
    }
}
