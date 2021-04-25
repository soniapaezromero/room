package com.example.paez_sonia_room.Hipica.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Nos hace las operaciones que queremos hacer con la base de datos
 */


@Dao
public abstract interface ReservaDao {
    @Transaction// Ordena la base por fehas dela mas reciente a la mas antigue
    @Query(value = "SELECT * FROM reservas ORDER BY fecha DESC")
    LiveData<List<Reserva>>  getReserva();
    @Transaction
    @Query("SELECT * FROM reservas WHERE idpaseo LIKE :idpaseo")
    LiveData <Reserva> getReserva(Integer idpaseo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)//Inerta datos en la tabla
    void addReserva(Reserva paseo);

    @Delete
    void deleteReserva(Reserva paseo);// Borra un registro de la tabal

    @Update
    void updateReserva(Reserva paseo);// Actualiza el registro
}
