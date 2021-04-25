package com.example.paez_sonia_room.Hipica.database;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Crea la abase de datos y ejecuta los disitinto hilos a traves de llamadas
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Reserva.class}, version = 1)
public abstract class Reservadatabase  extends RoomDatabase {
    private static Reservadatabase INSTANCE;
    public abstract ReservaDao reservaDao();


    public static Reservadatabase getInstance(Context context){
          if(INSTANCE== null) {
              synchronized (Reservadatabase.class) {
                  if (INSTANCE == null) {
                      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Reservadatabase.class, "reservas")
                              .fallbackToDestructiveMigration()
                              .addCallback(sRoomDatabaseCallback)
                              .build();
                  }
              }
                    }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ReservaDao reservaDao;
        PopulateDbAsync(Reservadatabase db){
            reservaDao= db.reservaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



}


