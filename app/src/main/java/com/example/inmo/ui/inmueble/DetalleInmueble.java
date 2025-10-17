package com.example.inmo.ui.inmueble;

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
import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;

public class DetalleInmueble extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;
    private Inmueble inmueble;


    public static DetalleInmueble newInstance() {
        return new DetalleInmueble();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inmueble = new Inmueble();
        mViewModel = new ViewModelProvider(this.getActivity()).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mViewModel.getMutableInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                binding.etDireccion.setText(inmueble.getDireccion());
                binding.etTipo.setText(inmueble.getTipo());
                binding.etUso.setText(inmueble.getUso());
                binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
                binding.cbDisponible.setChecked(inmueble.isDisponible());
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + inmueble.getImagen())
                        .placeholder(null)
                        .error("null")
                        .into(binding.ivFoto);
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mViewModel.updateInmueble(inmueble);
            }
        });

        mViewModel.recuperarInmueble(getArguments());
        return view;
    }



}