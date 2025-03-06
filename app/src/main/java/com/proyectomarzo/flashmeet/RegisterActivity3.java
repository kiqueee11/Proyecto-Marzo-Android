package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.gms.location.FusedLocationProviderClient;

public class RegisterActivity3 extends AppCompatActivity {

    private EditText etFecha, etDescripcion;
    private Spinner spinnerSexo;
    private TextView tvLocation;
    private Slider sliderDistance;
    private MaterialButton btnRegistrar;
    private String ubicacion = "No disponible";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        etFecha = findViewById(R.id.etFecha);
        etDescripcion = findViewById(R.id.et_description);
        spinnerSexo = findViewById(R.id.spinnerSex);
        tvLocation = findViewById(R.id.tvLocation);
        sliderDistance = findViewById(R.id.sliderDistance);
        btnRegistrar = findViewById(R.id.btnLogin);

        tvLocation.setText(ubicacion);

        sliderDistance.addOnChangeListener((slider, value, fromUser) -> {

        });
        String[] sexos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerSexo.setAdapter(adapter);


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
                editor.putString("ubicacion", ubicacion);
                editor.putString("distanciaBusqueda", distancia);

                editor.apply();

                Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}