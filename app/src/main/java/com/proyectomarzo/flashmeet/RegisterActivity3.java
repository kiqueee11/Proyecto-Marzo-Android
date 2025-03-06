package com.proyectomarzo.flashmeet;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class RegisterActivity3 extends AppCompatActivity {

    private EditText etFecha, etDescripcion;
    private Spinner spinnerSexo;
    private TextView tvLocation;
    private Slider sliderDistance;
    private MaterialButton btnRegistrar;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        etFecha = findViewById(R.id.etFecha);
        etDescripcion = findViewById(R.id.et_description);
        spinnerSexo = findViewById(R.id.spinnerSex);
        tvLocation = findViewById(R.id.tvLocation);
        sliderDistance = findViewById(R.id.sliderDistance);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Button getLocationButton = findViewById(R.id.btnGetLocation); // ID corregido

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });

        sliderDistance.addOnChangeListener((slider, value, fromUser) -> {
        });

        String[] sexos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        btnRegistrar = findViewById(R.id.btnRegister); // Asegúrate de que el ID es correcto aquí

        btnRegistrar.setOnClickListener(v -> {
            String fecha = etFecha.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String sexo = spinnerSexo.getSelectedItem().toString().trim();
            String distancia = String.valueOf(sliderDistance.getValue());

            if (fecha.isEmpty() || descripcion.isEmpty() || sexo.isEmpty()) {
                Toast.makeText(RegisterActivity3.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("fechaNacimiento", fecha);
                editor.putString("descripcion", descripcion);
                editor.putString("sexo", sexo);
                editor.putString("distanciaBusqueda", distancia);

                editor.apply();

                Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    System.out.println("Latitud: " + latitude + ", Longitud: " + longitude);

                    // Guarda las coordenadas en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitude", String.valueOf(latitude));
                    editor.putString("longitude", String.valueOf(longitude));
                    editor.apply();

                } else {
                    System.out.println("Ubicación no disponible");
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                System.out.println("Permiso de ubicación denegado");
            }
        }
    }
}