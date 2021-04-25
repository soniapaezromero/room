package com.example.paez_sonia_room.Hipica.adaptadores;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Adaptador del REcycler view que nos muestr nombre del jinete,FEcha y hora
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paez_sonia_room.Hipica.database.Reserva;
import com.example.paez_sonia_room.R;

import java.util.ArrayList;
import java.util.List;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.MyViewHolder> {
    private  Context context;
    public List<Reserva> listaPaseos;
    public ReservasAdapter(){
        this.listaPaseos= new ArrayList<>();
    }
    public ReservasAdapter(Context context){

    }
    public ReservasAdapter(Context context, List<Reserva> reservasPaseos) {
        this.context = context;
        this.listaPaseos = reservasPaseos;
    }

    public void setPaseos(List<Reserva> paseos){
        listaPaseos=paseos;
        notifyDataSetChanged();
    }
    public Reserva getPaseoAt(int position) {
        return listaPaseos.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView jinete,fecha,hora;



        public MyViewHolder(View itemview) {
            super(itemview);
            this.jinete = itemview.findViewById(R.id.jinete);
            this.fecha=itemview.findViewById(R.id.fecha);
            this.hora=itemview.findViewById(R.id.hora);



        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paseo, parent, false);
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(listaPaseos!=null) {
            Reserva paseo = listaPaseos.get(position);
            holder.jinete.setText(paseo.getNombreJinete().toString());
            holder.fecha.setText(paseo.getFechaPaseo().toString());
            holder.hora.setText(paseo.getHoraPaseo().toString());
        }else{
            holder.jinete.setText("No hay ninguna reserva");
        }

    }

    @Override
    public int getItemCount() {
        if(listaPaseos!= null) {
            return listaPaseos.size();
        }else{
            return 0;
        }
    }


}
