package com.proyectomarzo.flashmeet;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        setupListeners();
        initPhotoViews();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btn_edit);
        btnEdit2 = findViewById(R.id.btn_edit2); // Button to edit social media links
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvGender = findViewById(R.id.tv_gender);
        tvDescription = findViewById(R.id.tv_description);
        profileImage = findViewById(R.id.img_profile_background);
    }

    private void initPhotoViews() {
        photoViews = new ArrayList<>();
        photoViews.add(findViewById(R.id.img_photo1));
        photoViews.add(findViewById(R.id.img_photo2));
        photoViews.add(findViewById(R.id.img_photo3));
        photoViews.add(findViewById(R.id.img_photo4));
        photoViews.add(findViewById(R.id.img_photo5));
        photoViews.add(findViewById(R.id.img_photo6));

        photoUris = new ArrayList<>();
        for (int i = 0; i < photoViews.size(); i++) {
            photoUris.add(null);
        }

        for (int i = 0; i < photoViews.size(); i++) {
            final int index = i;
            photoViews.get(i).setOnClickListener(v -> handlePhotoClick(index));
        }
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

    private void handlePhotoClick(int index) {
        currentPhotoIndex = index;

        CharSequence[] options;
        if (photoUris.get(index) == null) {
            options = new CharSequence[]{"Agregar foto"};
        } else {
            options = new CharSequence[]{
                    "Cambiar foto",
                    "Eliminar foto",
                    index == 0 ? "Ya es foto de perfil" : "Poner como foto de perfil"
            };
        }

        new AlertDialog.Builder(this)
                .setTitle("Opciones de foto")
                .setItems(options, (dialog, which) -> {
                    if (photoUris.get(index) == null || which == 0) {
                        openPhotoPicker();
                    } else if (which == 1) {
                        removePhoto(index);
                    } else if (which == 2 && index != 0) {
                        setAsProfilePhoto(index);
                    }
                })
                .show();
    }

    private void setAsProfilePhoto(int index) {
        if (index == 0 || photoUris.get(index) == null) {
            return;
        }

        Uri newProfileUri = photoUris.get(index);
        Uri oldProfileUri = photoUris.get(0);

        photoUris.set(0, newProfileUri);
        photoUris.set(index, oldProfileUri);

        if (oldProfileUri != null) {
            loadPhotoImage(index, oldProfileUri);
        } else {
            photoViews.get(index).setImageDrawable(null);
            photoViews.get(index).setBackground(getDrawable(R.drawable.photo_placeholder));
        }
        loadPhotoImage(0, newProfileUri);

        loadProfileImage(newProfileUri);

        Toast.makeText(this, "Foto establecida como perfil", Toast.LENGTH_SHORT).show();
    }

    private void openPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (currentPhotoIndex >= 0 && currentPhotoIndex < photoViews.size()) {
                photoUris.set(currentPhotoIndex, selectedImageUri);
                loadPhotoImage(currentPhotoIndex, selectedImageUri);

                if (currentPhotoIndex == 0) {
                    loadProfileImage(selectedImageUri);
                }

                Toast.makeText(this, "Foto actualizada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadPhotoImage(int index, Uri uri) {
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(photoViews.get(index));

        photoViews.get(index).setBackground(null);
    }

    private void loadProfileImage(Uri uri) {
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(profileImage);
    }

    private void removePhoto(int index) {
        photoUris.set(index, null);
        photoViews.get(index).setImageDrawable(null);
        photoViews.get(index).setBackground(getDrawable(R.drawable.photo_placeholder));

        if (index == 0) {
            shiftPhotosUp();
        }

        Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show();
    }

    private void shiftPhotosUp() {
        for (int i = 1; i < photoUris.size(); i++) {
            if (photoUris.get(i) != null) {
                Uri photoToMove = photoUris.get(i);
                photoUris.set(0, photoToMove);
                photoUris.set(i, null);

                loadPhotoImage(0, photoToMove);
                loadProfileImage(photoToMove);

                photoViews.get(i).setImageDrawable(null);
                photoViews.get(i).setBackground(getDrawable(R.drawable.photo_placeholder));
                break;
            }
        }
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
            public void afterTextChanged(Editable s) {}
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
                        return;
                    }

                    tvName.setText(name);
                    tvAge.setText(age);
                    tvGender.setText(gender);
                    tvDescription.setText(description);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}
