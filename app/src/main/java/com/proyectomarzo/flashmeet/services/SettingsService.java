package com.proyectomarzo.flashmeet.services;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiClientSettings;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.models.SettingsResponse;
import com.proyectomarzo.flashmeet.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsService {


    Context context;

    public interface SettingsCallBack {
        void onSettingsReceived(SettingsResponse settingsResponse);
        void onError(Throwable t);
    }
    public void actualizarSettings(String token, SettingsCallBack settingsCallBack) {
        ApiService apiService = ApiClientSettings.getClient().create(ApiService.class);


        Call<SettingsResponse> call = apiService.getSettings(token);

        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.isSuccessful()) {

                    settingsCallBack.onSettingsReceived(response.body());
                    System.out.println("datos actualizados correctamente");
                } else {
                    settingsCallBack.onError(new Throwable("Error al obtener datos"));
                    System.out.println("Error al actualizar los datos");
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                settingsCallBack.onError(t);
                t.printStackTrace();
            }
        });
    }

    public void guardarSettings(String token, SettingsCallBack settingsCallBack) {
        ApiService apiService = ApiClientSettings.getClient().create(ApiService.class);


        Call<SettingsResponse> call = apiService.saveSettings(token);

        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.isSuccessful()) {
                    // Asegúrate de pasar la respuesta correctamente al callback
                    settingsCallBack.onSettingsReceived(response.body());
                    System.out.println("datos actualizados correctamente");
                } else {
                    settingsCallBack.onError(new Throwable("Error al obtener datos"));
                    System.out.println("Error al actualizar los datos");
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                settingsCallBack.onError(t);
                t.printStackTrace();
            }
        });
    }
}
