package com.example.inmo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inmo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    //Variables de llamada
    private SensorManager manager;
    private SensorEventListener manejador;
    private float accelerationCurrentValue;
    private float accelerationLastValue;
    private boolean isShaking = false;
    private float shakeThreshold = 3.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmueble, R.id.nav_inquilino, R.id.nav_contrato, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        binding.appBarMain.fab.setOnClickListener(v -> {
//            navController.navigate(R.id.crearFragment);
//        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        });

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observamos el evento del ViewModel
        viewModel.getLlamarEvent().observe(this, llamar -> {
            if (llamar != null && llamar) {
                llamarInmobiliaria();
                viewModel.onLlamadaRealizada();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onResume() {
        super.onResume();
        activaEscucha();
    }

    @Override
    protected void onPause() {
        super.onPause();
        desactivaEscucha();
    }

    public void activaEscucha() {
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensores.size() > 0) {
            manejador = new SensorEventListener() {
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}

                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        // Calculamos la aceleración total actual
                        float totalAcceleration = (float) Math.sqrt(x * x + y * y + z * z);

                        // Si es la primera lectura, inicializamos los valores base y salimos
                        if (accelerationLastValue == 0) {
                            accelerationLastValue = totalAcceleration;
                            accelerationCurrentValue = totalAcceleration;
                            return; // 👈 evita detectar un "shake" falso al inicio
                        }

                        accelerationLastValue = accelerationCurrentValue;
                        accelerationCurrentValue = totalAcceleration;

                        float delta = Math.abs(accelerationCurrentValue - accelerationLastValue);

                        // Detecta sacudida real
                        if (delta > shakeThreshold && !isShaking) {
                            isShaking = true;
                            viewModel.onShakeDetected(); // avisamos al ViewModel
                        }
                    }
                }

            };
            manager.registerListener(manejador, sensores.get(0), SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void desactivaEscucha() {
        if (manager != null && manejador != null)
            manager.unregisterListener(manejador);
        isShaking = false;
    }

    private void llamarInmobiliaria() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            isShaking = false;
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(android.net.Uri.parse("tel:2664553747"));
        startActivity(intent);

        Toast.makeText(this, "Llamando a la inmobiliaria...", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(() -> isShaking = false, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            llamarInmobiliaria();
        }
    }




}