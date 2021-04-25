package com.example.paez_sonia_room.Hipica.database;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Crea la tabla reservas que es la encargada de registrar los paseos
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservas")
public class Reserva {
    @PrimaryKey(autoGenerate = true)
    private Integer idpaseo;
    @ColumnInfo(name = "jinete")
    private String nombreJinete;
    @ColumnInfo(name = "fecha")
    private String fechaPaseo;
    @ColumnInfo(name = "caballo")
    private String nombreCaballo;
    @ColumnInfo(name = "hora")
    private String horaPaseo;
    @ColumnInfo(name = "telefono")
    private String telefono;
    @ColumnInfo(name = "comentario")
    private String comentario;

    public Reserva(Integer idpaseo) {
        this.idpaseo=idpaseo;
    }
    public Reserva(){

    }

    public Reserva(String nombreJinete, String fechaPaseo, String horaPaseo) {
        this.nombreJinete = nombreJinete;
        this.fechaPaseo = fechaPaseo;
        this.horaPaseo = horaPaseo;
    }

    @NonNull
    public Integer getIdpaseo() {
        return idpaseo;
    }

    public void setIdpaseo(@NonNull Integer idpaseo) {
        this.idpaseo = idpaseo;
    }

    @NonNull

    public String getNombreJinete() {
        return nombreJinete;
    }

    public void setNombreJinete( String nombreJinete) {
        this.nombreJinete = nombreJinete;
    }

    public String getFechaPaseo() {
        return fechaPaseo;
    }

    public void setFechaPaseo(String fechaPaseo) {
        this.fechaPaseo = fechaPaseo;
    }

    public String getNombreCaballo() {
        return nombreCaballo;
    }

    public void setNombreCaballo(String nombreCaballo) {
        this.nombreCaballo = nombreCaballo;
    }

    public String getHoraPaseo() {
        return horaPaseo;
    }

    public void setHoraPaseo(String horaPaseo) {
        this.horaPaseo = horaPaseo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idpaseo='" + idpaseo + '\'' +
                ", nombreJinete='" + nombreJinete + '\'' +
                ", fechaPaseo='" + fechaPaseo + '\'' +
                ", nombreCaballo='" + nombreCaballo + '\'' +
                ", horaPaseo='" + horaPaseo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
