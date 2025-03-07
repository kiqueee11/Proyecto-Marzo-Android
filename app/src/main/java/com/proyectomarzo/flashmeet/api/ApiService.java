package com.proyectomarzo.flashmeet.api;

import com.proyectomarzo.flashmeet.models.LoginRequest;
import com.proyectomarzo.flashmeet.models.LoginResponse;
import com.proyectomarzo.flashmeet.models.Message;
import com.proyectomarzo.flashmeet.models.MessageRequest;
import com.proyectomarzo.flashmeet.models.MessageResponse;
import com.proyectomarzo.flashmeet.models.RegisterRequest;
import com.proyectomarzo.flashmeet.models.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("auth/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("messages")
    Call<List<Message>> getMessages(@Header("Authorization") String token, @Query("recipient") String recipientEmail);

    @POST("messages")
    Call<MessageResponse> sendMessage(@Header("Authorization") String token, @Body MessageRequest messageRequest);
}
