package com.proyectomarzo.flashmeet;

import android.os.Bundle;

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
    List<Integer> friends = new ArrayList<>();
    List<Conversations> conversations = new ArrayList<>();
    MockConversationCreator mockConversationCreator = new MockConversationCreator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      conversations=  MockConversationCreator.createConversations();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (int i = 0; i < 1000; i++) {
            friends.add(i);

        }
        recyclerView = findViewById(R.id.friend_list_recycler_view);
        adapter = new FriendsListAdapter(this, conversations);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }
}