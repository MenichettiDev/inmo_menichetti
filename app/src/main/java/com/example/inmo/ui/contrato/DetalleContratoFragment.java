package com.example.inmo.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmo.R;
import com.example.inmo.databinding.FragmentDetalleContratoBinding;
import com.example.inmo.models.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mViewModel;
    private FragmentDetalleContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        mViewModel.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                Log.d("DetalleContratoFragment", "onChanged: contrato=" + contrato);
                if (contrato != null) {
                    binding.etIdContrato.setText(String.valueOf(contrato.getIdContrato()));
                    binding.cbEstadoContrato.setChecked(contrato.isEstado());
                    binding.etFechaInicio.setText(contrato.getFechaInicio());
                    binding.etFechaFinalizacion.setText(contrato.getFechaFinalizacion());
                    binding.etMonto.setText("$ " + contrato.getMontoAlquiler());
                    if (contrato.getInquilino() != null) {
                        binding.etIdInquilinoContrato.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
                    }
                    if (contrato.getInmueble() != null) {
                        binding.etIdInmuebleContrato.setText(contrato.getInmueble().getDireccion());
                    }
                }
            }
        });

        binding.btnPagos.setOnClickListener(v -> {
            if (mViewModel.getContrato().getValue() != null) {
                Bundle args = new Bundle();
                args.putInt("idContrato", mViewModel.getContrato().getValue().getIdContrato());
                androidx.navigation.fragment.NavHostFragment.findNavController(this)
                        .navigate(R.id.action_detalleContratoFragment_to_pagoFragment, args);
            }
        });

        if (getArguments() != null && getArguments().containsKey("idInmueble")) {
            int idInmueble = getArguments().getInt("idInmueble");
            Log.d("DetalleContratoFragment", "Recibido idInmueble: " + idInmueble);
            mViewModel.cargarContrato(idInmueble);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}