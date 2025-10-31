package com.example.inmo.ui.contrato;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmo.R;
import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;
import com.example.inmo.ui.inmueble.InmuebleAdapter;
import com.example.inmo.ui.inquilino.InquilinoAdapter;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderContrato>{

    private List<Inmueble> listado;
    private Context context;
    private LayoutInflater li;

    public ContratoAdapter(List<Inmueble> listado, Context context, LayoutInflater li) {
        this.listado = listado;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_inmueble, parent, false);
        return new ContratoAdapter.ViewHolderContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolderContrato holder, int position) {
        Inmueble inmueble = listado.get(position);
        holder.direccion.setText("Direccion: " + inmueble.getDireccion());
        holder.precio.setText("Precio: " + inmueble.getValor()+"");
//        Sino usar picasso -> com.squareup.picasso:picasso
//https://github.com/square/picasso
        Glide.with(context)
                .load(ApiClient.BASE_URL + inmueble.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.foto);
        ((ContratoAdapter.ViewHolderContrato) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("idInmueble", inmueble.getIdInmueble());
                Navigation.findNavController(view).navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listado.size() ;
    }

    public class ViewHolderContrato extends RecyclerView.ViewHolder{
        TextView direccion,precio;
        ImageView foto;
        public ViewHolderContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.txtDireccion);
            precio = itemView.findViewById(R.id.txtValor);
            foto = itemView.findViewById(R.id.imageView);
        }

    }

}
