package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.models.UserResponse;
import com.proyectomarzo.flashmeet.services.UserServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final int MAX_DESCRIPTION_WORDS = 50;
    private static final int REQUEST_PICK_IMAGE = 1001;

    private ImageView profileImage;
    private TextView tvName, tvAge, tvGender, tvDescription;
    private ImageButton btnEdit, btnBack, btnEdit2;
    private List<ImageView> photoViews;
    private List<Uri> photoUris;
    private int currentPhotoIndex = -1;

    private String instagramLink = "";
    private String facebookLink = "";
    private String twitterLink = "";
    private UserServices userServices;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userServices = new UserServices();

        // Obtener el token de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        initViews();
        setupListeners();

        if (!token.isEmpty()) {
            loadUserProfile();
        } else {
            Toast.makeText(this, "No hay token de autenticación", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserProfile() {
        if (token != null && !token.isEmpty()) {
            // Si el token es válido, puedes hacer la llamada a la API
            userServices.getUser(token, new UserServices.UserCallback() {
                @Override
                public void onUserReceived(UserResponse.UserData userData) {
                    updateProfileUI(userData);
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Error al cargar los datos del perfil", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateProfileUI(UserResponse.UserData userProfile) {
        // Asegúrate de que el objeto userProfile no sea nulo
        if (userProfile == null) {
            Toast.makeText(this, "Datos del perfil no válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar nombre
        tvName.setText(userProfile.getNombre() != null ? userProfile.getNombre() : "Nombre no disponible");

        // Actualizar la descripción
        tvDescription.setText(userProfile.getDescripcion() != null ? userProfile.getDescripcion() : "Descripción no disponible");

        // Actualizar sexo
        tvGender.setText(userProfile.getSexo() != null ? userProfile.getSexo() : "Sexo no disponible");

        // Actualizar edad
        tvAge.setText(userProfile.getFechaNacimiento() != null ? userProfile.getFechaNacimiento() : "Fecha de nacimiento no disponible");

        // Actualizar imagen de perfil
        if (userProfile.getImagen1() != null && !userProfile.getImagen1().isEmpty()) {
            Glide.with(this)
                    .load(userProfile.getImagen1())
                    .centerCrop()
                    .into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.photo_placeholder);
        }

        // Cargar otras imágenes
        loadImage(userProfile.getImagen2(), 0);
        loadImage(userProfile.getImagen3(), 1);
        loadImage(userProfile.getImagen4(), 2);
        loadImage(userProfile.getImagen5(), 3);
        loadImage(userProfile.getImagen6(), 4);
    }

    // Método auxiliar para cargar imágenes
    private void loadImage(String imageUrl, int index) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(photoViews.get(index));
        } else {
            photoViews.get(index).setImageResource(R.drawable.photo_placeholder); // Usar un placeholder si no hay imagen
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btn_edit);
        btnEdit2 = findViewById(R.id.btn_edit2);
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvGender = findViewById(R.id.tv_gender);
        tvDescription = findViewById(R.id.tv_description);
        profileImage = findViewById(R.id.img_profile_background);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnEdit.setOnClickListener(v -> showEditDialog());
        btnEdit2.setOnClickListener(v -> showEditSocialMediaDialog());

        findViewById(R.id.img_instagram).setOnClickListener(v -> openSocialMedia("Instagram", instagramLink));
        findViewById(R.id.img_facebook).setOnClickListener(v -> openSocialMedia("Facebook", facebookLink));
        findViewById(R.id.img_twitter).setOnClickListener(v -> openSocialMedia("Twitter", twitterLink));
    }

    private void openSocialMedia(String platform, String link) {
        if (link != null && !link.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        } else {
            Toast.makeText(this, "No hay red social", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditSocialMediaDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_social_media, null);
        EditText etInstagram = dialogView.findViewById(R.id.et_instagram);
        EditText etFacebook = dialogView.findViewById(R.id.et_facebook);
        EditText etTwitter = dialogView.findViewById(R.id.et_twitter);

        etInstagram.setText(instagramLink);
        etFacebook.setText(facebookLink);
        etTwitter.setText(twitterLink);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Editar Enlaces de Redes Sociales")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    instagramLink = etInstagram.getText().toString().trim();
                    facebookLink = etFacebook.getText().toString().trim();
                    twitterLink = etTwitter.getText().toString().trim();

                    Toast.makeText(this, "Enlaces actualizados", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showEditDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etAge = dialogView.findViewById(R.id.et_age);
        RadioGroup rgGender = dialogView.findViewById(R.id.rg_gender);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        TextView tvWordCount = dialogView.findViewById(R.id.tv_word_count);

        etName.setText(tvName.getText());
        etAge.setText(tvAge.getText());
        etDescription.setText(tvDescription.getText());

        if (tvGender.getText().toString().equals("Hombre")) {
            rgGender.check(R.id.rb_male);
        } else {
            rgGender.check(R.id.rb_female);
        }

        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int wordCount = s.toString().split("\\s+").length;
                tvWordCount.setText(wordCount + "/" + MAX_DESCRIPTION_WORDS + " palabras");

                if (wordCount > MAX_DESCRIPTION_WORDS) {
                    tvWordCount.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    tvWordCount.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        int initialWordCount = tvDescription.getText().toString().split("\\s+").length;
        tvWordCount.setText(initialWordCount + "/" + MAX_DESCRIPTION_WORDS + " palabras");

        new MaterialAlertDialogBuilder(this)
                .setTitle("Editar Perfil")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String age = etAge.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();

                    int selectedId = rgGender.getCheckedRadioButtonId();
                    RadioButton rbSelected = dialogView.findViewById(selectedId);
                    String gender = rbSelected.getText().toString();

                    if (description.split("\\s+").length > MAX_DESCRIPTION_WORDS) {
                        Toast.makeText(this, "La descripción excede el límite de palabras", Toast.LENGTH_SHORT).show();
                    } else {
                        // Lógica para guardar el perfil
                        updateUserProfile(name, age, gender, description);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void updateUserProfile(String name, String age, String gender, String description) {
        // Aquí deberías realizar una llamada API para guardar los cambios realizados en el perfil
        // Ejemplo usando el servicio de usuario:
        userServices.getUser(token, new UserServices.UserCallback() {
            @Override
            public void onUserReceived(UserResponse.UserData userData) {
                // Aquí actualizas la UI con los nuevos datos
                Toast.makeText(EditProfileActivity.this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                updateProfileUI(userData);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
