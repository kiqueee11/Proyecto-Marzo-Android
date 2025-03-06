package com.proyectomarzo.flashmeet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity2 extends AppCompatActivity {

    private MaterialButton btnImages;
    private GridLayout gridLayout;
    private ImageButton[] imageButtons = new ImageButton[6];
    private Uri[] imageUris = new Uri[6];
    private int selectedIndex = -1;

    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    if (selectedIndex != -1 && selectedImage != null) {
                        imageUris[selectedIndex] = selectedImage;
                        imageButtons[selectedIndex].setImageURI(selectedImage);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        btnImages = findViewById(R.id.btnImages);
        gridLayout = findViewById(R.id.grid);
        imageButtons[0] = findViewById(R.id.img_photo1);
        imageButtons[1] = findViewById(R.id.img_photo2);
        imageButtons[2] = findViewById(R.id.img_photo3);
        imageButtons[3] = findViewById(R.id.img_photo4);
        imageButtons[4] = findViewById(R.id.img_photo5);
        imageButtons[5] = findViewById(R.id.img_photo6);

        for (int i = 0; i < imageButtons.length; i++) {
            final int index = i;  // Asegúrate de que index sea final
            imageButtons[i].setOnClickListener(v -> openGallery(index));
        }

        btnImages.setOnClickListener(v -> {
            if (hasAtLeastOneImage()) {
                saveImagesToPreferences();
                goToNextActivity();
            } else {
                Toast.makeText(this, "Debes seleccionar al menos una imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToNextActivity() {

        startActivity(new Intent(RegisterActivity2.this, RegisterActivity3.class));
        finish();

    }

    private void openGallery(int index) {
        selectedIndex = index;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private boolean hasAtLeastOneImage() {
        for (Uri uri : imageUris) {
            if (uri != null) return true;
        }
        return false;
    }

    private void saveImagesToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < imageUris.length; i++) {
            if (imageUris[i] != null) {
                editor.putString("image" + i, imageUris[i].toString());
            }
        }
    }
}