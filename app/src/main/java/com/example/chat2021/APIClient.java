package com.example.chat2021;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {
    private static Retrofit retrofit = null;

    static Retrofit getClient() {

        // Afficher dans le logcat des traces des requêtes effectuées
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Configuration de GSON
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();

        // Création du client d'API
        retrofit = new Retrofit.Builder()
                .baseUrl("http://tomnab.fr/chat-api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        //TODO : permettre la modification de l'URL de base
        // lors du changement des préférences

        return retrofit;
    }
}