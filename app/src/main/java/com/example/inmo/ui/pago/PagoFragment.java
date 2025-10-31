package com.example.inmo.ui.pago;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmo.R;
import com.example.inmo.databinding.FragmentPagoBinding;

import java.util.Collections;

public class PagoFragment extends Fragment {

    private PagoViewModel mViewModel;
    private FragmentPagoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PagoViewModel.class);

        RecyclerView rv = binding.lista;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        PagoAdapter adapter = new PagoAdapter(Collections.emptyList());
        rv.setAdapter(adapter);

        mViewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            adapter.setPagos(pagos);
        });

        int idContrato = getArguments() != null ? getArguments().getInt("idContrato") : 0;
        mViewModel.cargarPagos(idContrato);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}