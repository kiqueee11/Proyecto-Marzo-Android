package com.proyectomarzo.flashmeet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.proyectomarzo.flashmeet.models.UserRequest;
import com.proyectomarzo.flashmeet.models.UserResponse;
import com.proyectomarzo.flashmeet.services.ChatService;
import com.proyectomarzo.flashmeet.services.UserServices;

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
    ChatService chatService;
    UserServices userServices;


    UserResponse.UserData userData;


    private static final int REVEAL_DELAY = 30 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatService = new ChatService();
        userServices = new UserServices();
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


        addFriendButton.setEnabled(false);
        viewProfileButton.setEnabled(false);
        addFriendButton.setOnClickListener(v -> confirmAddFriend());
        viewProfileButton.setOnClickListener(v -> showProfileMessage());
        handler.postDelayed(this::showIdentityPopup, REVEAL_DELAY);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("token",null);



    }

    private void sendMessage() {


        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            messageList.add(new Message(messageText, "Me", Message.TYPE_SENT));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.smoothScrollToPosition(messageList.size() - 1);
            messageInput.setText("");
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", null);

            if (userServices != null && token != null) {
                // Obtener los datos del usuario de forma segura
                userServices.getUser(token, new UserServices.UserCallback() {
                    @Override
                    public void onUserReceived(UserResponse.UserData userData) {
                        if (userData != null) {
                            // Ahora puedes usar el userData de manera segura
                            String email = userData.getEmail();
                            chatService.enviarMensaje(token, email, messageText);
                        } else {
                            Log.e("ChatActivity", "No se recibió userData.");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("ChatActivity", "Error al obtener los datos del usuario", t);
                    }
                });
            } else {
                Log.e("ChatActivity", "userServices o token es null");
            }

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

