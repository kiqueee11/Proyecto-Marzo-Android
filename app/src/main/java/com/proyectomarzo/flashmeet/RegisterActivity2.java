package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.proyectomarzo.flashmeet.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity2 extends AppCompatActivity {

    private MaterialButton btnImages;
    private GridLayout gridLayout;
    private ImageButton[] imageButtons = new ImageButton[6];
    private Uri[] imageUris = new Uri[6]; // Aquí guardamos las URIs de las imágenes
    private File[] imageFiles = new File[6]; // Aquí guardamos los archivos de las imágenes
    private int selectedIndex = -1;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    if (selectedIndex != -1 && selectedImage != null) {
                        imageUris[selectedIndex] = selectedImage;
                        imageButtons[selectedIndex].setImageURI(selectedImage);
                        try {
                            // Convertimos la URI seleccionada en un archivo
                            imageFiles[selectedIndex] = convertUriToFile(selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity2.this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        btnImages = findViewById(R.id.btnImages);
        gridLayout = findViewById(R.id.grid);
        imageButtons[0] = findViewById(R.id.img_photo1);
        imageButtons[1] = findViewById(R.id.img_photo2);
        imageButtons[2] = findViewById(R.id.img_photo3);
        imageButtons[3] = findViewById(R.id.img_photo4);
        imageButtons[4] = findViewById(R.id.img_photo5);
        imageButtons[5] = findViewById(R.id.img_photo6);

        for (int i = 0; i < imageButtons.length; i++) {
            final int index = i;
            imageButtons[i].setOnClickListener(v -> openGallery(index));
        }

        btnImages.setOnClickListener(v -> {
            if (hasAtLeastOneImage()) {
                sendImagesToBackend();
            } else {
                Toast.makeText(this, "Debes seleccionar al menos una imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery(int index) {
        selectedIndex = index;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private boolean hasAtLeastOneImage() {
        for (File file : imageFiles) {
            if (file != null) return true;
        }
        return false;
    }

    private File convertUriToFile(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File file = new File(getExternalFilesDir(null), "image_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            inputStream.close();
        }

        return file;
    }

    private void sendImagesToBackend() {
        //mandar las imagenes



        Intent intent = new Intent(RegisterActivity2.this, RegisterActivity3.class);
        startActivity(intent);
        finish();
    }

}
