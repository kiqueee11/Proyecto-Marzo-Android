package com.proyectomarzo.flashmeet.api;

import com.proyectomarzo.flashmeet.models.ImageResponse;
import com.proyectomarzo.flashmeet.models.LoginRequest;
import com.proyectomarzo.flashmeet.models.LoginResponse;
import com.proyectomarzo.flashmeet.models.Message;
import com.proyectomarzo.flashmeet.models.MessageRequest;
import com.proyectomarzo.flashmeet.models.MessageResponse;
import com.proyectomarzo.flashmeet.models.RegisterRequest;
import com.proyectomarzo.flashmeet.models.RegisterResponse;
import com.proyectomarzo.flashmeet.models.SettingsResponse;
import com.proyectomarzo.flashmeet.models.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @Multipart
    @POST("auth/auth/signup")
    Call<RegisterResponse> registerUser(
            @Part("nombre") RequestBody nombre,
            @Part("clave") RequestBody password,
            @Part("email") RequestBody email,
            @Part("sexo") RequestBody sexo,
            @Part("posicion") RequestBody posicion,
            @Part("fechaNacimiento") RequestBody fechaNacimiento,
            @Part("descripcion") RequestBody descripcion,
            @Part("distancia") RequestBody distancia,
            @Part MultipartBody.Part image1,
            @Part MultipartBody.Part image2,
            @Part MultipartBody.Part image3,
            @Part MultipartBody.Part image4,
            @Part MultipartBody.Part image5,
            @Part MultipartBody.Part image6
    );
    @Multipart
    @POST("auth/auth/iniciarSesion")
    Call<LoginResponse> loginUser(
            @Part("email") RequestBody email,
            @Part("clave") RequestBody clave

    );
    @POST("users/get-user-data")
    Call<UserResponse> getUserProfile(@Header("Authorization") String token);
    @POST("/settings/get-settings")
    Call<SettingsResponse> getSettings(@Header("Authorization") String token);
    @POST("/settings/save-settings")
    Call<SettingsResponse> saveSettings(@Header("Authorization") String token);
    @GET("/get-media")
    Call<ImageResponse> getMedia(@Query("fileName") String fileName);
    @GET("/messages")
    Call<List<Message>> getMessages(@Header("Authorization") String token, @Query("recipient") String recipientEmail);

    @POST("/messages")
    Call<MessageResponse> sendMessage(@Header("Authorization") String token, @Body MessageRequest messageRequest);
}
