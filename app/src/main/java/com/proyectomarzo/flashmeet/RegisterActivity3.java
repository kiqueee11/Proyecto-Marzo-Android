package com.proyectomarzo.flashmeet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.proyectomarzo.flashmeet.api.ApiClient;
import com.proyectomarzo.flashmeet.api.ApiService;
import com.proyectomarzo.flashmeet.models.RegisterRequest;
import com.proyectomarzo.flashmeet.models.RegisterResponse;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity3 extends AppCompatActivity {

    private TextView etFecha, etDescripcion;
    private Spinner spinnerSexo;
    private TextView tvLocation;
    private Slider sliderDistance;
    private MaterialButton btnRegistrar;
    private String ubicacion = "No disponible";

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private double latitude;
    private double longitude;
    private String coordenadas;
    private FusedLocationProviderClient fusedLocationClient;

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Button getLocationButton = findViewById(R.id.btnGetLocation); // ID corregido

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });



        etFecha.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


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


        String[] sexos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        btnRegistrar.setOnClickListener(v -> {
            String fechaNacimiento = etFecha.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String sexo = spinnerSexo.getSelectedItem().toString().trim();
            int distancia = (int) sliderDistance.getValue();

            if (fechaNacimiento.isEmpty() || descripcion.isEmpty() || sexo.isEmpty()) {
                Toast.makeText(RegisterActivity3.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("fechaNacimiento", fechaNacimiento);
                editor.putString("descripcion", descripcion);
                editor.putString("sexo", sexo);
                editor.putString("ubicacion", ubicacion);
                editor.putInt("distancia", distancia);

                editor.apply();

                Context context;
                File[] imageFiles = new File[6];




                String nombre = sharedPreferences.getString("nombre", null);
                String password = sharedPreferences.getString("password",null);
                String email = sharedPreferences.getString("email",null);
                String imagePath1 = sharedPreferences.getString("image_path_" + 0, null);
                String imagePath2 = sharedPreferences.getString("image_path_" + 1, null);
                String imagePath3 = sharedPreferences.getString("image_path_" + 2, null);
                String imagePath4 = sharedPreferences.getString("image_path_" + 3, null);
                String imagePath5 = sharedPreferences.getString("image_path_" + 4, null);
                String imagePath6 = sharedPreferences.getString("image_path_" + 5, null);
                sexo = sharedPreferences.getString("sexo",null);
                String posicion = sharedPreferences.getString("posicion",null);
                fechaNacimiento = sharedPreferences.getString("fechaNacimiento",null);
                descripcion = sharedPreferences.getString("descripcion",null);
                distancia = sharedPreferences.getInt("distancia",-1);


                assert imagePath1 != null;
                File file1 = new File(imagePath1);
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                MultipartBody.Part image1Part = MultipartBody.Part.createFormData("image1", file1.getName(), requestBody1);

                assert imagePath2 != null;
                File file2 = new File(imagePath2);
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                MultipartBody.Part image2Part = MultipartBody.Part.createFormData("image2", file2.getName(), requestBody2);

                assert imagePath3 != null;
                File file3 = new File(imagePath3);
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), file3);
                MultipartBody.Part image3Part = MultipartBody.Part.createFormData("image3", file3.getName(), requestBody3);

                assert imagePath4 != null;
                File file4 = new File(imagePath4);
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("image/*"), file4);
                MultipartBody.Part image4Part = MultipartBody.Part.createFormData("image4", file4.getName(), requestBody4);

                assert imagePath5 != null;
                File file5 = new File(imagePath5);
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("image/*"), file5);
                MultipartBody.Part image5Part = MultipartBody.Part.createFormData("image5", file5.getName(), requestBody5);

                assert imagePath6 != null;
                File file6 = new File(imagePath6);
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("image/*"), file6);
                MultipartBody.Part image6Part = MultipartBody.Part.createFormData("image6", file6.getName(), requestBody6);

                RequestBody nombreRequest = RequestBody.create(MediaType.parse("text/plain"), nombre);
                RequestBody passwordRequest = RequestBody.create(MediaType.parse("text/plain"), password);
                RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody sexoRequest = RequestBody.create(MediaType.parse("text/plain"), sexo);
                RequestBody posicionRequest = RequestBody.create(MediaType.parse("text/plain"), posicion);
                RequestBody fechaNacimientoRequest = RequestBody.create(MediaType.parse("text/plain"), fechaNacimiento);
                RequestBody descripcionRequest = RequestBody.create(MediaType.parse("text/plain"), descripcion);
                String distanciaStr = String.valueOf(distancia);
                RequestBody distanciaRequest = RequestBody.create(MediaType.parse("text/plain"), distanciaStr);



                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<RegisterResponse> call = apiService.registerUser(
                        nombreRequest,
                        passwordRequest,
                        emailRequest,
                        sexoRequest,
                        posicionRequest,
                        fechaNacimientoRequest,
                        descripcionRequest,
                        distanciaRequest,
                        image1Part,
                        image2Part,
                        image3Part,
                        image4Part,
                        image5Part,
                        image6Part
                );



                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            RegisterResponse responseModel = response.body();
                            String status = responseModel.getStatus();
                            String message = responseModel.getMessage();
                            Toast.makeText(RegisterActivity3.this, "Respuesta: " + message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity3.this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity3.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    System.out.println("Latitud: " + latitude + ", Longitud: " + longitude);
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    String formattedLatitude = decimalFormat.format(latitude);
                    String formattedLongitude = decimalFormat.format(longitude);
                    ubicacion = formattedLatitude+","+formattedLongitude;
                    editor.putString("posicion",ubicacion);
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
