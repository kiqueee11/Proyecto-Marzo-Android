package com.proyectomarzo.flashmeet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectomarzo.flashmeet.datos_mock.Conversations;
import com.proyectomarzo.flashmeet.datos_mock.MockConversationCreator;
import com.proyectomarzo.flashmeet.friends_recycler_view.FriendsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    private static final String TAG = "FriendListActivity";
    private FriendsListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Integer> friends = new ArrayList<>();
    private List<Conversations> conversations = new ArrayList<>();
    private MockConversationCreator mockConversationCreator = new MockConversationCreator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activar la funcionalidad de Edge-to-Edge
        EdgeToEdge.enable(this);

        // Establecer el layout de la actividad
        setContentView(R.layout.activity_friend_list);

        // Configurar el listener para el manejo de márgenes del sistema (barras de estado, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Crear las conversaciones para la vista de la lista de amigos
        conversations = MockConversationCreator.createConversations();

        // Inicializar los datos de amigos (esto puede ser modificado según el modelo real)
        for (int i = 0; i < 1000; i++) {
            friends.add(i);  // En un caso real, aquí agregarías amigos reales
        }

        // Configurar el RecyclerView y su adaptador
        recyclerView = findViewById(R.id.friend_list_recycler_view);
        adapter = new FriendsListAdapter(this, conversations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());
    }
}
