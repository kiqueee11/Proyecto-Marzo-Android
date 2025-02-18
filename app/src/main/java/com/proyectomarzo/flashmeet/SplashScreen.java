package com.proyectomarzo.flashmeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Animación para el logo
        ImageView splashLogo = findViewById(R.id.splashLogo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        splashLogo.startAnimation(fadeIn);

        // Carga de imagen de fondo con Glide
        ImageView glideBackground = findViewById(R.id.splashGlideBackground);
        Glide.with(this)
                .load(R.drawable.gradient) // Asegúrate de tener una imagen en drawable
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .centerCrop()
                .into(glideBackground);

        // Llamamos a launchMain después de 3 segundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish(); // Cierra SplashScreen
        }, 3000);
    }
}
