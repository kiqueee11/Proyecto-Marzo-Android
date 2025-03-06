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
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        sliderAgeRange = findViewById(R.id.sliderAgeRange);
        sliderDistance = findViewById(R.id.sliderDistance);
        togglePreferences = findViewById(R.id.togglePreferences);
        switchProfileVisible = findViewById(R.id.switchProfileVisible);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        sliderAgeRange.addOnChangeListener((slider, value, fromUser) -> {
            Toast.makeText(SettingsActivity.this,
                    "Edad seleccionada: " + value,
                    Toast.LENGTH_SHORT).show();
        });

        sliderDistance.addOnChangeListener((slider, value, fromUser) -> {
            Toast.makeText(SettingsActivity.this,
                    "Distancia de búsqueda: " + value,
                    Toast.LENGTH_SHORT).show();
        });

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

        switchProfileVisible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String visibilityStatus = isChecked ? "Perfil visible" : "Perfil oculto";
            Toast.makeText(SettingsActivity.this, visibilityStatus, Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(SettingsActivity.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
        });

        btnDeleteAccount.setOnClickListener(v -> {
            Toast.makeText(SettingsActivity.this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
        });
    }
}
