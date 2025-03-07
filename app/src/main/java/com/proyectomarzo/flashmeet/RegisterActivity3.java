package com.proyectomarzo.flashmeet;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity3 extends AppCompatActivity {

    private TextView etFecha, etDescripcion;
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

        etFecha = findViewById(R.id.tvFecha);
        etDescripcion = findViewById(R.id.et_description);
        spinnerSexo = findViewById(R.id.spinnerSex);
        tvLocation = findViewById(R.id.tvLocation);
        sliderDistance = findViewById(R.id.sliderDistance);
        btnRegistrar = findViewById(R.id.btnLogin);

        tvLocation.setText(ubicacion);


        etFecha.setOnClickListener(v -> {
            // Obtén la fecha actual
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Crea un DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity3.this,
                    (view, yearSelected, monthOfYear, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(yearSelected, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        etFecha.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Configuración del Spinner para Sexo
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
