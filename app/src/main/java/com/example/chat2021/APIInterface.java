package com.example.chat2021;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface APIInterface {

    @GET("conversations")
    Call<ListConversation> doGetListConversation(@Header("hash") String hash);

    @GET("conversations/{id}/messages")
    Call<ListMessage> doGetListMessage(@Header("hash") String hash, @Path("id") int convId);

    /*
    //  req. GET : @Query
    //  req. POST : @Body (object qui sera désérialisé)
    // @Header : ajouter des entêtes

    @FormUrlEncoded
    @POST("authenticate")
    Call<ResponseBody> doConnect(@Field("user") String pseudo, @Field("password") String passe);
    // {"version":1.3,"success":true,"status":202,"hash":"4e28dafe87d65cca1482d21e76c61a06"}

    @GET("users")
    Call<UserList> doGetUserList(@Header("hash") String hash);
    // Il est possible d'utiliser un intercepteur pour ajouter une entête à toutes les requêtes
    // Cf. https://guides.codepath.com/android/consuming-apis-with-retrofit
    // {"version":1.3,"success":true,"status":200,"users":[{"id":"1","pseudo":"tom"},{"id":"2","pseudo":"isa"}]}

    @GET("users/{id}")
    Call<User> doGetUserFromId(@Header("hash") String hash, @Path("id") int userId);
    // {"version":1.3,"success":true,"status":200,"user":{"id":"1","pseudo":"tom"}}

    @GET("users?")
    Call<UserList> doGetUserListStatus(@Header("hash") String hash, @Query("status") String status); //authorized / blacklisted

    @PUT("users/{id}?")
    Call<ResponseBody> doUpdateUserStatus(@Header("hash") String hash,@Path("id") int userId,@Query("status") String status);

    @FormUrlEncoded
    @POST("users")
    Call<User> doCreateUser(@Header("hash") String hash, @Field("user") String user, @Field("password") String password);

    */


}