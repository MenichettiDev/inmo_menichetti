package com.example.inmo.ui.logout;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inmo.R;
import com.example.inmo.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;
    private FragmentLogoutBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mostrarDialogoDeSalida();

        return root;
    }



    private void mostrarDialogoDeSalida() {
        if (getContext() == null) { // Evita NullPointerException si el contexto no está disponible
            return;
        }
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmar Salida")
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() != null) {
                            getActivity().finishAffinity(); // Cierra todas las actividades de la aplicación
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getParentFragment() != null) { // Verifica si está en un NavHostFragment
                            NavHostFragment.findNavController(LogoutFragment.this).popBackStack();
                        } else if (getActivity() != null) {
                        }
                        dialog.dismiss(); // Cierra el diálogo
                    }
                })
                .setCancelable(false) // Opcional: Evita que el diálogo se cierre al tocar fuera o con el botón "Atrás"
                .setIcon(android.R.drawable.ic_dialog_alert) // Ícono opcional
                .show();
    }

}