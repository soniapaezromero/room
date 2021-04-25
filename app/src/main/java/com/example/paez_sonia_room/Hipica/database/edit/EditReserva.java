package com.example.paez_sonia_room.Hipica.database.edit;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad para Editar Registros recoge los datos los pasa a la actividad princiapl paa incluirlos en la base de datos
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paez_sonia_room.Hipica.database.Reserva;
import com.example.paez_sonia_room.Hipica.database.ReservaRepositorio;
import com.example.paez_sonia_room.Hipica.database.add.AddReserva;
import com.example.paez_sonia_room.R;
import com.example.paez_sonia_room.databinding.ActivityAddReservaBinding;
import com.example.paez_sonia_room.databinding.ActivityEditReservaBinding;

import java.util.Calendar;
import java.util.List;

public class EditReserva extends AppCompatActivity   implements View.OnClickListener{
    private ActivityEditReservaBinding bindingEdit;
    private ReservaRepositorio repositorio;
    public final int RESERVAS_MAX =2;
    private Reserva reservaModificada;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    public static final String  EXTRA_EDITID="com.example.paez_sonia_room.Hipica.database.edit.EDITID";
    public static final String  EXTRA_EDITJINETE="com.example.paez_sonia_room.Hipica.database.edit.EXTRA_EDITJINETE;";
    public static final String  EXTRA_EDITFECHA="com.example.paez_sonia_room.Hipica.database.edit.EXTRA_EDITFECHA;";
    public static final String  EXTRA_EDITCABALLO="com.example.paez_sonia_room.Hipica.database.edit.EXTRA_EDITCABALLO;";
    public static final String  EXTRA_EDITHORA="com.example.paez_sonia_room.Hipica.database.edit.EXTRA_EDITHORA";
    public static final String  EXTRA_EDITELEFONO="com.example.paez_sonia_room.Hipica.database.edit.EXTRA_EDITELEFONO";
    public static final String  EXTRA_EDITCOMENTARIO="com.example.paez_sonia_room.Hipica.database.edit.EDITCOMENTARIO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reserva);
        bindingEdit = ActivityEditReservaBinding.inflate(getLayoutInflater());
        View view = bindingEdit.getRoot();
        setContentView(view);
       // setTitle("Modificar reserva");
        Intent intent= getIntent();
        if(intent.hasExtra(EXTRA_EDITID)) {
            setTitle("Modificar reserva");
            Log.e("editar", String.valueOf(intent.getIntExtra(EXTRA_EDITID,-1)));
            bindingEdit.editnombreJinete.setText(intent.getStringExtra(EXTRA_EDITJINETE));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITJINETE));
            bindingEdit.editfechaPaseo.setText(intent.getStringExtra(EXTRA_EDITFECHA));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITFECHA));
            bindingEdit.editcaballo.setText(intent.getStringExtra(EXTRA_EDITCABALLO));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITCABALLO));
            bindingEdit.edithoraPaseo.setText(intent.getStringExtra(EXTRA_EDITHORA));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITHORA));
            bindingEdit.edittelefono.setText(intent.getStringExtra(EXTRA_EDITELEFONO));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITELEFONO));
            bindingEdit.editcomentario.setText(intent.getStringExtra(EXTRA_EDITCOMENTARIO));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITCOMENTARIO));
        }

        //configuramos los clicks
        bindingEdit.editfechaPaseo.setOnClickListener(this);
        bindingEdit.edithoraPaseo.setOnClickListener(this);
        bindingEdit.botonEditar.setOnClickListener(this);
        bindingEdit.botonCancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (v== bindingEdit.botonEditar){
           modificarPaseo();

        }
        if(v== bindingEdit.botonCancelar){
           finish();
        }
        if(v== bindingEdit.editfechaPaseo){
           mostrarCalendario();
        }
        if( v== bindingEdit.edithoraPaseo){
            obtenerHora();
        }

    }

    private void modificarPaseo() {
        bindingEdit.editnombreJinete.setError(null);
        bindingEdit.editfechaPaseo.setError(null);
        bindingEdit.editcaballo.setError(null);
        bindingEdit.edittelefono.setError(null);
        String jinete = bindingEdit.editnombreJinete.getText().toString();
        String fecha = bindingEdit.editfechaPaseo.getText().toString();
        String nombreCaballo = bindingEdit.editcaballo.getText().toString();
        String horaPAseo = bindingEdit.edithoraPaseo.getText().toString();
        String telefonoMovil = bindingEdit.edittelefono.getText().toString();
        String coment = bindingEdit.editcomentario.getText().toString();

            if ("".equals(jinete)) {
                bindingEdit.editnombreJinete.setText("Tienes que introducir el dato");
                bindingEdit.editnombreJinete.requestFocus();
                return;
            }
            if ("".equals(fecha)) {
                bindingEdit.editfechaPaseo.setText("Tienes que introducir el dato");
                bindingEdit.editfechaPaseo.requestFocus();
                return;
            }
            if ("".equals(nombreCaballo)) {
                bindingEdit.editcaballo.setText("Tienes que introducir el dato");
                bindingEdit.editcaballo.requestFocus();
                return;
            }
            if ("".equals(horaPAseo)) {
                bindingEdit.edithoraPaseo.setText("Tienes que introducir el dato");
                bindingEdit.edithoraPaseo.requestFocus();
                return;
            }
            if ("".equals(telefonoMovil)) {
                bindingEdit.edittelefono.setText("Tienes que introducir el dato");
                bindingEdit.edittelefono.requestFocus();
                return;
            }
            if (telefonoMovil.length() != 9) {
                Toast.makeText(EditReserva.this, "El telefono tiene que tener 9 digitos",
                        Toast.LENGTH_SHORT).show();
                bindingEdit.edittelefono.requestFocus();
                return;
            }
        Intent datoEdit=new Intent();
        datoEdit.putExtra(EXTRA_EDITJINETE,jinete);
        datoEdit.putExtra(EXTRA_EDITFECHA,fecha);
        datoEdit.putExtra(EXTRA_EDITCABALLO,nombreCaballo);
        datoEdit.putExtra(EXTRA_EDITHORA,horaPAseo);
        datoEdit.putExtra(EXTRA_EDITELEFONO,telefonoMovil);
        datoEdit.putExtra(EXTRA_EDITCOMENTARIO,coment);
        Integer id=getIntent().getIntExtra(EXTRA_EDITID,-1);
        Log.e("DAtos Enviados ID",String.valueOf(id));
        if(id!= -1) {
         datoEdit.putExtra(EXTRA_EDITID,id);
        }
        setResult(RESULT_OK, datoEdit);
        Log.e("DAtos Enviados","Editar encia datos");
        finish();

    }

    private void mostrarCalendario() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            private static final String BARRA = "/";

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = "";
                if (dayOfMonth < 10) {
                    diaFormateado = "0" + String.valueOf(dayOfMonth);
                } else {
                    diaFormateado = String.valueOf(dayOfMonth);
                }
                String mesFormateado = "";
                if (mesActual < 10) {
                    mesFormateado = "0" + String.valueOf(mesActual);
                } else {
                    mesFormateado = String.valueOf(mesActual);
                }
                //Muestro la fecha con el formato deseado
                bindingEdit.editfechaPaseo.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    private void obtenerHora() {

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                view.setIs24HourView(true);
                String horaFormateada = "";
                if((hourOfDay<17)||(hourOfDay>20)) {
                    Toast.makeText(EditReserva.this,"Los paseos empiezan a 17:00 y el último es a las 20:00.",Toast.LENGTH_SHORT).show();
                    horaFormateada= "17";
                }else{
                    horaFormateada=String.valueOf(hourOfDay);
                }
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = "";
                if(minute != 0){
                    Toast.makeText(EditReserva.this,"Los paseos empiezan en las horas en punto, se pondra en punto .",Toast.LENGTH_SHORT).show();
                    minutoFormateado="0";
                }else{
                    minutoFormateado=String.valueOf(minute);
                }


                //Muestro la hora con el formato deseado
                bindingEdit.edithoraPaseo.setText(horaFormateada + ":0" + minutoFormateado );
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);
        recogerHora.show();
    }
}