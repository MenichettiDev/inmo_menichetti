package com.example.inmo.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmo.R;
import com.example.inmo.databinding.FragmentPasswordBinding;
import com.example.inmo.databinding.FragmentPerfilBinding;

public class PasswordFragment extends Fragment {

    private PasswordViewModel mViewModel;
    private FragmentPasswordBinding binding;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =new ViewModelProvider(this).get(PasswordViewModel.class);

        binding = FragmentPasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnGuardarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.cambiarBoton(
                        binding.tvContraseniaActual.getText().toString(),
                        binding.tvNuevaContrasenia.getText().toString(),
                        binding.tvConfirmarContrasenia.getText().toString());
            }
        });
    
        return root;
    }



}