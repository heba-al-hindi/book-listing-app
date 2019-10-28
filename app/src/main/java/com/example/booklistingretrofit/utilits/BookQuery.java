package com.example.booklistingretrofit.utilits;


import android.icu.text.CaseMap;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.TypeVariable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BookQuery {


    @GET
    Call<JsonObject> getBooks(@Url String url) ;



}
