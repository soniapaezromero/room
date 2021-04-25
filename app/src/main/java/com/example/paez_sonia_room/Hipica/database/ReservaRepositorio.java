package com.example.paez_sonia_room.Hipica.database;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Mi Clase repositorio crea las distintas operacione sde insertar, actualizar, eliminar registros
 */

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReservaRepositorio {
    @SuppressLint("StaticFieldLeak")
    public  ReservaDao reservaDao;
    private LiveData<List<Reserva>> allReservas;

    public ReservaRepositorio(Application aplication) {
        Reservadatabase reservadatabase = Reservadatabase.getInstance(aplication);
        reservaDao = reservadatabase.reservaDao();
        allReservas=reservaDao.getReserva();


    }

    public  LiveData<List<Reserva>> getReserva() {
         return  allReservas;
    }

    public LiveData<Reserva> getReserva(Integer id) {
        return reservaDao.getReserva(id);
    }

    public void addReserva(Reserva paseo) {
        new InsertAsynctask(reservaDao).execute(paseo);
    }

    public void updateReserva(Reserva paseo) {
        new UpdateAsynctask(reservaDao).execute(paseo);
    }

    public void deleteReserva(Reserva paseo) {
        new DeleteAsynctask(reservaDao).execute(paseo);
        ;
    }
// Asyntak para crear registro
    private static class InsertAsynctask extends AsyncTask<Reserva, Void, Void> {
        ReservaDao reservaDao;

        public InsertAsynctask(ReservaDao reservaDao) {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas) {
            reservaDao.addReserva(reservas[0]);
            return null;
        }
    }
    // Asyntak para actualizar registro
    private static class UpdateAsynctask extends AsyncTask<Reserva, Void, Void> {
        ReservaDao reservaDao;

        public UpdateAsynctask(ReservaDao reservaDao) {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas) {
            reservaDao.updateReserva(reservas[0]);
            return null;
        }
    }
    // Asyntak para borrar registro
    private static class DeleteAsynctask extends AsyncTask<Reserva, Void, Void> {
        ReservaDao reservaDao;

        public DeleteAsynctask(ReservaDao reservaDao) {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas) {
            reservaDao.deleteReserva(reservas[0]);
            return null;
        }
    }
}



