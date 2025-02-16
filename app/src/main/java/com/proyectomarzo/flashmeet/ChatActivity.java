package com.proyectomarzo.flashmeet;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.proyectomarzo.flashmeet.adapter.MessageAdapter;
import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.databinding.ActivityChatBinding;
import com.proyectomarzo.flashmeet.models.Message;
import com.proyectomarzo.flashmeet.models.MessageRequest;
import com.proyectomarzo.flashmeet.models.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ApiService apiService;
    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    private String token;
    private String email;
    private String recipientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener token guardado
        token = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE).getString("token", "");
        email = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE).getString("email", "");

        // Configurar RecyclerView
        adapter = new MessageAdapter(messageList, email);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Permitir seleccionar destinatario
        binding.etRecipient.setVisibility(View.VISIBLE);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etMessage.getText().toString().trim();
                if (content.isEmpty()) return;

                if (recipientEmail == null || recipientEmail.isEmpty()) {
                    recipientEmail = binding.etRecipient.getText().toString().trim();
                    if (recipientEmail.isEmpty()) {
                        Toast.makeText(ChatActivity.this, "Ingrese correo del destinatario", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    binding.etRecipient.setVisibility(View.GONE);
                    binding.tvRecipient.setText("Chateando con: " + recipientEmail);
                    binding.tvRecipient.setVisibility(View.VISIBLE);
                    loadMessages();
                }

                sendMessage(content);
                binding.etMessage.setText("");
            }
        });
    }

    private void loadMessages() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Call<List<Message>> call = apiService.getMessages("Bearer " + token, recipientEmail);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear();
                    messageList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    binding.recyclerView.scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String content) {
        MessageRequest messageRequest = new MessageRequest(recipientEmail, content);

        Call<MessageResponse> call = apiService.sendMessage("Bearer " + token, messageRequest);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Message newMessage = new Message(
                            response.body().getId(),
                            email,
                            recipientEmail,
                            content,
                            response.body().getTimestamp()
                    );
                    messageList.add(newMessage);
                    adapter.notifyItemInserted(messageList.size() - 1);
                    binding.recyclerView.scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}