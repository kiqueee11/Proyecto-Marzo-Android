package com.proyectomarzo.flashmeet;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    private GridLayout gridLayout;
    private View dimBackground;
    private Button btnImages, btnConfirm;
    private ProgressBar progressBar;

    // Launcher para seleccionar una imagen
    ActivityResultLauncher<Intent> seleccionarImagenLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializamos los componentes
        imageButton1 = findViewById(R.id.img_photo1);
        imageButton2 = findViewById(R.id.img_photo2);
        imageButton3 = findViewById(R.id.img_photo3);
        imageButton4 = findViewById(R.id.img_photo4);
        imageButton5 = findViewById(R.id.img_photo5);
        imageButton6 = findViewById(R.id.img_photo6);
        gridLayout = findViewById(R.id.grid);
        dimBackground = findViewById(R.id.dimBackground);
        btnImages = findViewById(R.id.btnImages);
        btnConfirm = findViewById(R.id.btnConfirm);
        progressBar = findViewById(R.id.progressBar);

        // Inicializamos el launcher para seleccionar imágenes
        seleccionarImagenLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        ImageButton selectedButton = getSelectedImageButton();
                        if (selectedButton != null) {
                            selectedButton.setImageURI(imageUri);  // Establecer la imagen
                        }
                    }
                }
        );

        // Configurar botón para abrir el grid de imágenes
        btnImages.setOnClickListener(v -> abrirGrid());

        // Configurar botones de imágenes
        imageButton1.setOnClickListener(v -> seleccionarImagen(imageButton1));
        imageButton2.setOnClickListener(v -> seleccionarImagen(imageButton2));
        imageButton3.setOnClickListener(v -> seleccionarImagen(imageButton3));
        imageButton4.setOnClickListener(v -> seleccionarImagen(imageButton4));
        imageButton5.setOnClickListener(v -> seleccionarImagen(imageButton5));
        imageButton6.setOnClickListener(v -> seleccionarImagen(imageButton6));

        // Configurar botón de confirmar
        btnConfirm.setOnClickListener(v -> confirmar());
    }

    // Método para abrir el popup
    private void abrirGrid() {
        int visible = gridLayout.getVisibility();
        if (visible == GONE) {
            gridLayout.setVisibility(VISIBLE);
            dimBackground.setVisibility(VISIBLE);
            btnImages.setEnabled(false); // Desactivar el botón de imágenes
        } else {
            gridLayout.setVisibility(GONE);
            dimBackground.setVisibility(GONE);
            btnImages.setEnabled(true); // Activar el botón de imágenes
        }
    }

    // Método para seleccionar una imagen
    private void seleccionarImagen(ImageButton button) {
        // Guardamos el botón seleccionado y lanzamos la galería
        button.requestFocus();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        seleccionarImagenLauncher.launch(intent);
    }

    // Método para obtener el ImageButton seleccionado (el que tiene el foco)
    private ImageButton getSelectedImageButton() {
        View focusedView = getCurrentFocus();
        if (focusedView instanceof ImageButton) {
            return (ImageButton) focusedView;
        }
        return null;
    }

    // Método para confirmar la selección de las imágenes y cerrar el popup
    private void confirmar() {
        Toast.makeText(this, "Imágenes guardadas correctamente", Toast.LENGTH_SHORT).show();
        gridLayout.setVisibility(GONE);
        dimBackground.setVisibility(GONE);
        btnImages.setEnabled(true); // Volver a habilitar el botón de imágenes
    }
}
