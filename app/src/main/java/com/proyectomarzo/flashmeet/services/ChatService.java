package com.proyectomarzo.flashmeet.services;

import com.proyectomarzo.flashmeet.api.ApiClientChat;
import com.proyectomarzo.flashmeet.api.ApiClientSettings;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.models.Message;
import com.proyectomarzo.flashmeet.models.MessageRequest;
import com.proyectomarzo.flashmeet.models.MessageResponse;
import com.proyectomarzo.flashmeet.models.SettingsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatService {

    public void enviarMensaje(String token,String email, String contenido) {

        ApiService apiService = ApiClientChat.getClient().create(ApiService.class);


        Call<MessageResponse> call = apiService.sendMessage(token,new MessageRequest(email,contenido));

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {

                    System.out.println("datos actualizados correctamente");
                } else {

                    System.out.println("Error al actualizar los datos");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }

    public void recibirMensaje(String token,String email) {

        ApiService apiService = ApiClientChat.getClient().create(ApiService.class);

        Call<List<Message>> call = apiService.getMessages(token,email);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {

                    System.out.println("datos actualizados correctamente");
                } else {

                    System.out.println("Error al actualizar los datos");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}
