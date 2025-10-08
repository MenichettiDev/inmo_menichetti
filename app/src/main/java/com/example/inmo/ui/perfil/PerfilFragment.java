package com.example.inmo.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmo.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel perfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        perfilViewModel =new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        perfilViewModel.getMutableEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
            }
        });

        perfilViewModel.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btPerfil.setText(s);
            }
        });

        binding.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilViewModel.cambiarBoton(binding.btPerfil.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString());
            }
        });

        //mutable del propietario
        perfilViewModel.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<com.example.inmo.models.Propietario>() {

            @Override
            public void onChanged(com.example.inmo.models.Propietario propietario) {
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmail.setText(propietario.getEmail());
            }
        });

        perfilViewModel.getPerfil();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}