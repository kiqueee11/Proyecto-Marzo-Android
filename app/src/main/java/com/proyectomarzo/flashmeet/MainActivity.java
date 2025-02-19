package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.proyectomarzo.flashmeet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView profileImage;
    Button settingsButton;
    Button friendsButton;
    MaterialButton meetButton;  // Botón "Meet"
    ActivityMainBinding binding;

    // Handler y Runnable para vibración continua
    private Handler handler = new Handler();
    private Runnable vibrateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configuración de márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias de los botones
        settingsButton = findViewById(R.id.settings_button);
        friendsButton = findViewById(R.id.friends_button);
        meetButton = findViewById(R.id.meet_button);  // Referencia al botón "Meet"

        settingsButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
        meetButton.setOnClickListener(this);

        // Configuración de la imagen de perfil con borde y destello
        profileImage = findViewById(R.id.profile_activity_profile_image);
        profileImage.setClipToOutline(true);
        profileImage.setOutlineProvider(new ViewOutlineProvider() {

            @Override
            public void getOutline(View view, Outline outline) {
                // Define un contorno circular con el radio basado en el tamaño de la imagen
                int diameter = Math.min(view.getWidth(), view.getHeight());
                outline.setOval(0, 0, diameter, diameter); // Establecer forma circular
            }
        });

        // Configurar los botones con fondo transparente
        settingsButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        friendsButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));



        // Configurar la acción al presionar la imagen de perfil
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.settings_button) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        if (view.getId() == R.id.friends_button) {
            Intent intent = new Intent(this, FriendListActivity.class);
            startActivity(intent);
        }

        if(view.getId() == R.id.meet_button) {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        }
    }


}
