package com.proyectomarzo.flashmeet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RangeSlider sliderAgeRange;
    private Slider sliderDistance;
    private MaterialButtonToggleGroup togglePreferences;
    private SwitchMaterial switchProfileVisible;
    private MaterialButton btnLogout;
    private MaterialButton btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);  // Asegúrate de que este es el layout correcto

        // Inicializando vistas
        btnBack = findViewById(R.id.btnBack);
        sliderAgeRange = findViewById(R.id.sliderAgeRange);
        sliderDistance = findViewById(R.id.sliderDistance);
        togglePreferences = findViewById(R.id.togglePreferences);
        switchProfileVisible = findViewById(R.id.switchProfileVisible);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // Configuración del botón de retroceso
        btnBack.setOnClickListener(v -> {
            onBackPressed();  // Llama al método que maneja el retroceso
        });

        // Configuración del rango de edad
        sliderAgeRange.addOnChangeListener((slider, value, fromUser) -> {
            // Muestra el rango seleccionado en un Toast (puedes hacer algo más con estos valores)
            Toast.makeText(SettingsActivity.this,
                    "Edad seleccionada: " + value,
                    Toast.LENGTH_SHORT).show();
        });

        // Configuración de la distancia de búsqueda
        sliderDistance.addOnChangeListener((slider, value, fromUser) -> {
            // Muestra la distancia seleccionada en un Toast
            Toast.makeText(SettingsActivity.this,
                    "Distancia de búsqueda: " + value,
                    Toast.LENGTH_SHORT).show();
        });

        // Configuración de las preferencias (genera un cambio cuando el usuario seleccione una opción)
        togglePreferences.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            String preference = "Seleccionado: ";
            if (checkedId == R.id.btnMale) {
                preference += "Hombre";
            } else if (checkedId == R.id.btnBoth) {
                preference += "Ambos";
            } else if (checkedId == R.id.btnFemale) {
                preference += "Mujer";
            }
            Toast.makeText(SettingsActivity.this, preference, Toast.LENGTH_SHORT).show();
        });

        // Configuración de la visibilidad del perfil
        switchProfileVisible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String visibilityStatus = isChecked ? "Perfil visible" : "Perfil oculto";
            Toast.makeText(SettingsActivity.this, visibilityStatus, Toast.LENGTH_SHORT).show();
        });

        // Configuración del botón de cerrar sesión
        btnLogout.setOnClickListener(v -> {
            // Lógica de cierre de sesión (por ejemplo, ir a la pantalla de login)
            Toast.makeText(SettingsActivity.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
            // Aquí iría el código para cerrar la sesión y redirigir al LoginActivity
        });

        // Configuración del botón de eliminar cuenta
        btnDeleteAccount.setOnClickListener(v -> {
            // Lógica para eliminar cuenta (puedes mostrar un diálogo de confirmación)
            Toast.makeText(SettingsActivity.this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
            // Aquí iría el código para eliminar la cuenta
        });
    }
}
