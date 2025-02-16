package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//    Button btn_login;
//    Button btn_register;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        btn_login=(Button)findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(v.getId()==R.id.btn_login){
//            Intent intent=new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//
//
//    }
//}

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.databinding.ActivityLoginBinding;
import com.proyectomarzo.flashmeet.models.LoginRequest;
import com.proyectomarzo.flashmeet.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);

        // Verificar si ya hay token guardado
        SharedPreferences prefs = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            navigateToChatActivity();
        }
        if(v.getId()==R.id.register_button){
            Intent intent=new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    // Guardar token
                    SharedPreferences prefs = getSharedPreferences("ChatAppPrefs", MODE_PRIVATE);
                    prefs.edit().putString("token", response.body().getToken()).apply();
                    prefs.edit().putString("email", email).apply();

                    navigateToChatActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToChatActivity() {
        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}