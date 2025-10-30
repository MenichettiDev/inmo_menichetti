package com.example.inmo.ui.inquilino;

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
import com.example.inmo.models.Inquilino;
import com.example.inmo.request.ApiClient;
import com.example.inmo.ui.inmueble.InmuebleAdapter;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolderInquilino> {

    private List<Inmueble> listado;
    private Context context;
    private LayoutInflater li;

    public InquilinoAdapter(List<Inmueble> listado, Context context, LayoutInflater li) {
        this.listado = listado;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public InquilinoAdapter.ViewHolderInquilino onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_inmueble, parent, false);
        return new InquilinoAdapter.ViewHolderInquilino(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InquilinoAdapter.ViewHolderInquilino holder, int position) {
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
        ((InquilinoAdapter.ViewHolderInquilino) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("idInmueble", inmueble.getIdInmueble());
                Navigation.findNavController(view).navigate(R.id.detalleInquilinoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listado.size() ;
    }

    public class ViewHolderInquilino extends RecyclerView.ViewHolder{
        TextView direccion,precio;
        ImageView foto;
        public ViewHolderInquilino(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.txtDireccion);
            precio = itemView.findViewById(R.id.txtValor);
            foto = itemView.findViewById(R.id.imageView);
        }

    }

}
