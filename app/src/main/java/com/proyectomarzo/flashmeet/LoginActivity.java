package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomarzo.flashmeet.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Comentamos todo lo relacionado con la verificación del token
        /*
        apiService = ApiClient.getClient().create(ApiService.class);

        // Comentar la verificación del token guardado
        SharedPreferences prefs = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            navigateToChatActivity();
            return;
        }
        */

        // Evento de clic para el botón de login (sin necesidad de poner usuario ni contraseña)
        binding.btnLogin.setOnClickListener(v -> {
            // Redirigir a MainActivity directamente al hacer clic en el botón
            navigateToChatActivity();
        });

        // Evento de clic para ir al registro
        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Método para redirigir a MainActivity
    private void navigateToChatActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Cierra la actividad de login
    }
}
