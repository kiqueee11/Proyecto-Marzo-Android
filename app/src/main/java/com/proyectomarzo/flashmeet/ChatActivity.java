package com.proyectomarzo.flashmeet;



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

    private static final int REVEAL_DELAY = 30 * 1000;

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
        backButton.setOnClickListener(v -> finish());


        addFriendButton.setVisibility(View.GONE);
        viewProfileButton.setVisibility(View.GONE);

        addFriendButton.setOnClickListener(v -> confirmAddFriend());
        viewProfileButton.setOnClickListener(v -> showProfileMessage());


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
        addFriendButton.setVisibility(View.VISIBLE);
        viewProfileButton.setVisibility(View.VISIBLE);

        userImage.setImageResource(R.drawable.mock_user);
        userName.setText("Usuario Misterioso");
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

