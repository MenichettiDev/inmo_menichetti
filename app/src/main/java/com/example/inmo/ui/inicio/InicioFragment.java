package com.example.inmo.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.inmo.R;
import com.example.inmo.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista primero
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        // 2. Usar getViewLifecycleOwner() para observar el LiveData de forma segura
        inicioViewModel.getMapaActualMutableLiveData().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                // 3. Obtener el SupportMapFragment usando el ChildFragmentManager
                //    Esto es más robusto si el mapa está anidado dentro de este fragmento.
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    // 4. Pasar el callback correcto a getMapAsync.
                    //    Asegúrate de que 'mapaActual' implemente OnMapReadyCallback.
                    mapFragment.getMapAsync(mapaActual);
                }
            }
        });

        inicioViewModel.cargarMapa();

        return root;
    }

}