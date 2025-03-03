package com.proyectomarzo.flashmeet;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.proyectomarzo.flashmeet.adapter.MessageAdapter;
//import com.proyectomarzo.flashmeet.api.ApiClient;
//import com.proyectomarzo.flashmeet.api.ApiService;
//import com.proyectomarzo.flashmeet.databinding.ActivityChatBinding;
//import com.proyectomarzo.flashmeet.models.Message;
//import com.proyectomarzo.flashmeet.models.MessageRequest;
//import com.proyectomarzo.flashmeet.models.MessageResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ChatActivity extends AppCompatActivity {
//
//    private ActivityChatBinding binding;
//    private ApiService apiService;
//    private List<Message> messageList = new ArrayList<>();
//    private MessageAdapter adapter;
//    private String token;
//    private String email;
//    private String recipientEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityChatBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Obtener token guardado
//        token = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE).getString("token", "");
//        email = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE).getString("email", "");
//
//        // Configurar RecyclerView
//        adapter = new MessageAdapter(messageList, email);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        binding.recyclerView.setAdapter(adapter);
//
//        apiService = ApiClient.getClient().create(ApiService.class);
//
//        // Permitir seleccionar destinatario
//        binding.etRecipient.setVisibility(View.VISIBLE);
//
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String content = binding.etMessage.getText().toString().trim();
//                if (content.isEmpty()) return;
//
//                if (recipientEmail == null || recipientEmail.isEmpty()) {
//                    recipientEmail = binding.etRecipient.getText().toString().trim();
//                    if (recipientEmail.isEmpty()) {
//                        Toast.makeText(ChatActivity.this, "Ingrese correo del destinatario", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    binding.etRecipient.setVisibility(View.GONE);
//                    binding.tvRecipient.setText("Chateando con: " + recipientEmail);
//                    binding.tvRecipient.setVisibility(View.VISIBLE);
//                    loadMessages();
//                }
//
//                sendMessage(content);
//                binding.etMessage.setText("");
//            }
//        });
//    }
//
//    private void loadMessages() {
//        binding.progressBar.setVisibility(View.VISIBLE);
//
//        Call<List<Message>> call = apiService.getMessages("Bearer " + token, recipientEmail);
//        call.enqueue(new Callback<List<Message>>() {
//            @Override
//            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                binding.progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful() && response.body() != null) {
//                    messageList.clear();
//                    messageList.addAll(response.body());
//                    adapter.notifyDataSetChanged();
//                    binding.recyclerView.scrollToPosition(messageList.size() - 1);
//                } else {
//                    Toast.makeText(ChatActivity.this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Message>> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
//                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void sendMessage(String content) {
//        MessageRequest messageRequest = new MessageRequest(recipientEmail, content);
//
//        Call<MessageResponse> call = apiService.sendMessage("Bearer " + token, messageRequest);
//        call.enqueue(new Callback<MessageResponse>() {
//            @Override
//            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Message newMessage = new Message(
//                            response.body().getId(),
//                            email,
//                            recipientEmail,
//                            content,
//                            response.body().getTimestamp()
//                    );
//                    messageList.add(newMessage);
//                    adapter.notifyItemInserted(messageList.size() - 1);
//                    binding.recyclerView.scrollToPosition(messageList.size() - 1);
//                } else {
//                    Toast.makeText(ChatActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MessageResponse> call, Throwable t) {
//                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectomarzo.flashmeet.R;
import com.proyectomarzo.flashmeet.adapter.MessageAdapter;
import com.proyectomarzo.flashmeet.models.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText messageInput;
    private ImageButton sendButton, backButton, addFriendButton, viewProfileButton;
    private ImageView userImage;
    private TextView userName;
    private Random random;
    private Handler handler;

    private static final int REVEAL_DELAY = 30 * 1000; // 5 minutos en milisegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_view);
        messageInput = findViewById(R.id.text_input);
        sendButton = findViewById(R.id.send);
        backButton = findViewById(R.id.arrow);
        addFriendButton = findViewById(R.id.add_friend);
        viewProfileButton = findViewById(R.id.see_perfil);
        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        random = new Random();
        handler = new Handler(Looper.getMainLooper());

        sendButton.setOnClickListener(v -> sendMessage());
        backButton.setOnClickListener(v -> finish()); // Cierra la actividad

        // Ocultar botones al inicio
        /*addFriendButton.setVisibility(View.GONE);
        viewProfileButton.setVisibility(View.GONE);*/
        addFriendButton.setEnabled(false);
        viewProfileButton.setEnabled(false);
        addFriendButton.setOnClickListener(v -> confirmAddFriend());
        viewProfileButton.setOnClickListener(v -> showProfileMessage());

        // Iniciar el temporizador para mostrar el popup
        handler.postDelayed(this::showIdentityPopup, REVEAL_DELAY);
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            messageList.add(new Message(messageText, "Me", Message.TYPE_SENT));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.smoothScrollToPosition(messageList.size() - 1);
            messageInput.setText("");

            simulateResponse();
        }
    }

    private void simulateResponse() {
        recyclerView.postDelayed(() -> {
            String botResponse = getRandomResponse();
            messageList.add(new Message(botResponse, "Bot", Message.TYPE_RECEIVED));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.smoothScrollToPosition(messageList.size() - 1);
        }, 1500);
    }

    private String getRandomResponse() {
        String[] responses = {
                "¡Interesante! Cuéntame más.",
                "No estoy seguro, ¿qué piensas tú?",
                "Eso suena genial.",
                "Jajaja, buena esa.",
                "¿Podrías explicarlo mejor?",
                "Me gusta cómo piensas.",
                "¿Tienes más detalles?",
                "Eso es un misterio para mí.",
                "¡Wow! No me lo esperaba.",
                "Parece algo importante, ¿cierto?"
        };
        return responses[random.nextInt(responses.length)];
    }

    private void showIdentityPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Revelar identidad")
                .setMessage("¿Quieres revelar la identidad de la persona con la que chateas?")
                .setPositiveButton("Sí", (dialog, which) -> revealIdentity())
                .setNegativeButton("No", (dialog, which) -> resetChat())
                .setCancelable(false)
                .show();
    }

    private void revealIdentity() {
        userImage.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        /*addFriendButton.setVisibility(View.VISIBLE);
        viewProfileButton.setVisibility(View.VISIBLE);*/
        viewProfileButton.setImageResource(R.drawable.add_amigo);
        addFriendButton.setImageResource(R.drawable.ver_perfil);
        addFriendButton.setEnabled(true);
        viewProfileButton.setEnabled(true);
        userImage.setImageResource(R.drawable.mock_user); // Imagen ficticia
        userName.setText("Usuario Misterioso"); // Nombre ficticio
    }

    private void resetChat() {
        messageList.clear();
        messageAdapter.notifyDataSetChanged();
    }

    private void confirmAddFriend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar amigo")
                .setMessage("¿Deseas agregar a esta persona como amigo?")
                .setPositiveButton("Sí", (dialog, which) -> showFriendAddedMessage())
                .setNegativeButton("No", null)
                .show();
    }

    private void showFriendAddedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amigo agregado")
                .setMessage("La persona ha sido agregada a tu lista de amigos.")
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void showProfileMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Perfil del usuario")
                .setMessage("Dirigiendo al perfil...")
                .setPositiveButton("Aceptar", null)
                .show();
    }
}

