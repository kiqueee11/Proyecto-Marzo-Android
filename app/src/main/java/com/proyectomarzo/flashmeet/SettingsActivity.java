package com.proyectomarzo.flashmeet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.button.MaterialButton;
import com.proyectomarzo.flashmeet.models.SettingsResponse;
import com.proyectomarzo.flashmeet.models.UserRequest;
import com.proyectomarzo.flashmeet.models.UserResponse;
import com.proyectomarzo.flashmeet.services.SettingsService;
import com.proyectomarzo.flashmeet.services.UserServices;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RangeSlider sliderAgeRange;
    private Slider sliderDistance;
    private MaterialButtonToggleGroup togglePreferences;
    private SwitchMaterial switchProfileVisible;
    private MaterialButton btnLogout;
    private MaterialButton btnDeleteAccount;
    private SettingsService settingsService;
    private SettingsResponse settingsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsService = new SettingsService();
        settingsResponse = new SettingsResponse();
        btnBack = findViewById(R.id.btnBack);
        sliderAgeRange = findViewById(R.id.sliderAgeRange);
        sliderDistance = findViewById(R.id.sliderDistance);
        togglePreferences = findViewById(R.id.togglePreferences);
        switchProfileVisible = findViewById(R.id.switchProfileVisible);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnBack.setOnClickListener(v -> {
            saveData();
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

        changeData();
    }
    public void saveData(){

        SharedPreferences sharedPreferences = this.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null && !token.isEmpty()) {

            settingsService.guardarSettings(token, new SettingsService.SettingsCallBack() {

                @Override
                public void onSettingsReceived(SettingsResponse settingsResponse) {
                    List<Float> values = sliderAgeRange.getValues();
                    String minage = String.valueOf(values.get(0));
                    String maxage = String.valueOf(values.get(1));
                    int miage = Integer.parseInt(minage);
                    int maage = Integer.parseInt(maxage);
                    settingsResponse.setMinAge(miage);
                    settingsResponse.setMaxAge(maage);
                    int distancias = (int) sliderDistance.getValue();
                    settingsResponse.setDistance(distancias);
                    int idboton = togglePreferences.getCheckedButtonId();
                    Button preferencia = findViewById(idboton);
                    String textPreferencias = (String) preferencia.getText();
                    if (textPreferencias.equals("Hombre")){
                        settingsResponse.setSexualPreference("Hombre");
                    } else if (textPreferencias.equals("Mujer")) {
                        settingsResponse.setSexualPreference("Mujer");
                    }else{
                        settingsResponse.setSexualPreference("Ambos");
                    }
                    if (switchProfileVisible.isActivated()){
                        settingsResponse.setVisible(true);
                    }else {
                        settingsResponse.setVisible(false);
                    }

                }

                @Override
                public void onError(Throwable t) {

                }


            });

        }


    }
    public void changeData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);  // Asegúrate de que obtienes un token válido

        if (token != null && !token.isEmpty()) {
            // Si el token es válido, puedes hacer la llamada a la API
            settingsService.actualizarSettings(token, new SettingsService.SettingsCallBack() {
                @Override
                public void onSettingsReceived(SettingsResponse settingsResponse) {
                    if (settingsResponse != null) {
                        int minAge = settingsResponse.getMinAge();
                        int maxAge = settingsResponse.getMaxAge();
                        int distancia = settingsResponse.getDistance();
                        String preferencias = settingsResponse.getSexualPreference();
                        boolean visible = settingsResponse.isVisible();

                        sliderAgeRange.setValues((float) minAge, (float) maxAge);
                        sliderDistance.setValue(distancia);

                        if (preferencias.equals("Hombre")) {
                            togglePreferences.check(R.id.btnMale);
                        } else if (preferencias.equals("Mujer")) {
                            togglePreferences.check(R.id.btnFemale);
                        } else {
                            togglePreferences.check(R.id.btnBoth);
                        }

                        switchProfileVisible.setChecked(visible);
                    } else {
                        Log.e("SettingsActivity", "No se recibió settingsResponse.");
                    }
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("SettingsActivity", "Error al obtener los datos del usuario", t);
                }
            });
        } else {
            Log.e("SettingsActivity", "Token inválido o nulo.");
        }
    }
}
