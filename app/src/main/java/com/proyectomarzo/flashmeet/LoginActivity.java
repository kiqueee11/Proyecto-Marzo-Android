package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.databinding.ActivityLoginBinding;
import com.proyectomarzo.flashmeet.models.LoginResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextView etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        binding.btnLogin.setOnClickListener(v -> {
            String mail = etEmail.getText().toString().trim();
            String clave = etPassword.getText().toString().trim();
            if (checkP()) {

                RequestBody passwordRequest = RequestBody.create(MediaType.parse("text/plain"), clave);
                RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), mail);

                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<LoginResponse> call = apiService.loginUser(
                        emailRequest,
                        passwordRequest
                );

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Login exitoso
                            LoginResponse responseModel = response.body();
                            String status = responseModel.getToken();
                            // Guardar el token o cualquier otro dato necesario para la sesión
                            Toast.makeText(LoginActivity.this, "Login exitoso. Token: " + status, Toast.LENGTH_SHORT).show();

                            // Avanzar a la siguiente actividad solo si el login fue exitoso
                            navigateToChatActivity();
                        } else {
                            // Si la respuesta no fue exitosa, mostrar un mensaje de error
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Error de conexión
                        Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean checkP() {
        String mail = etEmail.getText().toString().trim();
        String clave = etPassword.getText().toString().trim();

        // Verificar que el correo y la contraseña no estén vacíos
        if (mail.isEmpty()) {
            Toast.makeText(LoginActivity.this, "El correo no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (clave.isEmpty()) {
            Toast.makeText(LoginActivity.this, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar que el correo tenga un formato válido
        if (!isValidEmail(mail)) {
            Toast.makeText(LoginActivity.this, "El correo no tiene un formato válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Si todo está correcto, retorna true
        return true;
    }

    // Método para verificar el formato del correo electrónico usando una expresión regular
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    private void navigateToChatActivity() {
        // Este método solo se llamará si el login es exitoso
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
