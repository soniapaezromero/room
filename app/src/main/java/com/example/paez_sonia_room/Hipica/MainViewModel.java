package com.example.paez_sonia_room.Hipica;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Clase View Model que se encarga de conectar la activida principal con le calse repositoio de la base de datos
 */

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paez_sonia_room.Hipica.database.Reserva;
import com.example.paez_sonia_room.Hipica.database.ReservaRepositorio;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ReservaRepositorio repositorio;
    private LiveData<List<Reserva>> todasReservas;
    public MainViewModel( Application application) {
        super(application);
        repositorio= new ReservaRepositorio(application);
        todasReservas= repositorio.getReserva();

    }

    public void Insert(Reserva reserva){// Inserta una Reserva en la bse de datos
        repositorio.addReserva(reserva);
    }
    public void Update(Reserva reserva){// Modifica la vbase de datos
        repositorio.updateReserva(reserva);
    }
    public void Delete(Reserva reserva){// Borra registro
        repositorio.deleteReserva(reserva);
    }
    public  LiveData<List<Reserva>> getAllReservas(){// Muestra todads las Reservas
        return todasReservas;
    }
}
