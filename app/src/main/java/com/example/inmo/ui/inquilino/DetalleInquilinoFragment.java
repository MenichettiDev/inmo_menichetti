package com.example.inmo.ui.inquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmo.R;
import com.example.inmo.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmo.databinding.FragmentDetalleInquilinoBinding;
import com.example.inmo.models.Inmueble;
import com.example.inmo.models.Inquilino;
import com.example.inmo.request.ApiClient;
import com.example.inmo.ui.inmueble.DetalleInmuebleViewModel;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel mViewModel;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this.getActivity()).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        mViewModel.getMutableInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.etIdInquilino.setText(String.valueOf(inquilino.getIdInquilino()));
                binding.etApellido.setText(inquilino.getApellido());
                binding.etNombre.setText(inquilino.getNombre());
                binding.etDni.setText(String.valueOf(inquilino.getDni()));
                binding.etTelefono.setText(inquilino.getTelefono());
                binding.etEmail.setText(inquilino.getEmail());
            }
        });


//        mViewModel.recuperarInquilino(getArguments());
        if (getArguments() != null && getArguments().containsKey("idInmueble")) {
            int idInmueble = getArguments().getInt("idInmueble");
            mViewModel.recuperarInquilino(idInmueble);
        }
        return view;

    }



}