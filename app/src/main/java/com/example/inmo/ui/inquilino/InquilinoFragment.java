package com.example.inmo.ui.inquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmo.R;
import com.example.inmo.databinding.FragmentInmuebleBinding;
import com.example.inmo.databinding.FragmentInquilinoBinding;
import com.example.inmo.models.Inmueble;
import com.example.inmo.models.Inquilino;
import com.example.inmo.ui.inmueble.InmuebleAdapter;
import com.example.inmo.ui.inmueble.InmuebleViewModel;

import java.util.List;

public class InquilinoFragment extends Fragment {

    private FragmentInquilinoBinding binding;
    private InquilinoViewModel viewModel;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         viewModel =
                new ViewModelProvider(this).get(InquilinoViewModel.class);

        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMutableInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, getContext(), getLayoutInflater());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.lista.setLayoutManager(glm);
                binding.lista.setAdapter(adapter);

            }

        });

        viewModel.getInmueblesAlquilados();
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}