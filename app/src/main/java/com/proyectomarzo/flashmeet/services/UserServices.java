package com.proyectomarzo.flashmeet.services;

import android.content.Context;

import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiClientUser;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServices {

    public interface UserCallback {
        void onUserReceived(UserResponse.UserData userData);
        void onError(Throwable t);
    }
    Context context;
    public void getUser(String token, UserCallback userCallback) {

        ApiService apiService = ApiClientUser.getClient().create(ApiService.class);


        Call<UserResponse> call = apiService.getUserProfile(token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {

                    System.out.println("datos actualizados correctamente");
                } else {

                    System.out.println("Error al actualizar los datos");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }
}
