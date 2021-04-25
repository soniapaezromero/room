package com.example.paez_sonia_room.Hipica.busqueda;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad Muestra los resultados dela busqueda
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.paez_sonia_room.Hipica.MainActivityHipica;
import com.example.paez_sonia_room.Hipica.MainViewModel;
import com.example.paez_sonia_room.Hipica.adaptadores.ReservasAdapter;
import com.example.paez_sonia_room.Hipica.database.Reserva;
import com.example.paez_sonia_room.R;
import com.example.paez_sonia_room.databinding.ActivityListaBusquedaBinding;

import java.util.ArrayList;
import java.util.List;

public class Lista_Busqueda extends AppCompatActivity {
    public ActivityListaBusquedaBinding activityListaBusquedaBinding;
    Buscador buscador;
    private ReservasAdapter adapter;
    private MainViewModel mainViewModel;
    public List<Reserva>reservasEncontradas;
    String fechaEncontrada="";
    String horaEncontrada= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__busqueda);
        activityListaBusquedaBinding=ActivityListaBusquedaBinding.inflate(getLayoutInflater());
        View v= activityListaBusquedaBinding.getRoot();
        setContentView(v);
        setTitle("Reservas encontradas");
        //REcogemos la fecha y hora de la clase Buscador
        Bundle extras = getIntent().getExtras();
        fechaEncontrada=extras.getString("fechaBuscada");
        horaEncontrada= extras.getString("horaBuscada");
        reservasEncontradas= new ArrayList<>();
        adapter= new ReservasAdapter();
        activityListaBusquedaBinding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        activityListaBusquedaBinding.recyclerViewSearch.setAdapter(adapter);
        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllReservas().observe(this, new Observer<List<Reserva>>() {
            @Override
            public void onChanged(List<Reserva> reservas) {
            for(int i= 0;i< reservas.size();i++){//Hacemo0s el filtro
                Reserva r= reservas.get(i);
               if(r.getFechaPaseo().equals(fechaEncontrada)&&r.getHoraPaseo().equals(horaEncontrada)) {
                   reservasEncontradas.add(r) ;
               }
            }
            adapter.setPaseos(reservasEncontradas);// Pasamos los datos al recycler View
            }

        });

    }
//Menu tiene un boton de cerrar que nos lleva a la actividad principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_busqueda, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar:
               finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}