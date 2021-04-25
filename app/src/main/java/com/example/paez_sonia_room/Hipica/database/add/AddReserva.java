package com.example.paez_sonia_room.Hipica.database.add;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad para añadir Registros recoge los datos los pasa a la ctividad princiapl paa incluirlos en la base de datos
 */
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.paez_sonia_room.R;
import com.example.paez_sonia_room.databinding.ActivityAddReservaBinding;

import java.util.Calendar;
import java.util.List;

public class AddReserva extends AppCompatActivity implements View.OnClickListener  {
    private ActivityAddReservaBinding bindingAdd;
    private ReservaRepositorio repositorio;

    private Reserva reserva;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    public static final String  EXTRA_ADDJINETE="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDJINETE";
    public static final String  EXTRA_ADDFECHA="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDFECHA";
    public static final String  EXTRA_ADDCABALLO="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDCABALLO";
    public static final String  EXTRA_ADDHORA="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDHORA";
    public static final String  EXTRA_ADDTELEFONO="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDTELEFONO";
    public static final String  EXTRA_ADDCOMENTARIO="com.example.paez_sonia_room.Hipica.database.add.EXTRA_ADDCOMENTARIO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reserva);
        bindingAdd = ActivityAddReservaBinding.inflate(getLayoutInflater());
        View view = bindingAdd.getRoot();
        setContentView(view);
        setTitle("Añadir Reserva");
        bindingAdd.addhoraPaseo.setOnClickListener(this);
        bindingAdd.addfechaPaseo.setOnClickListener(this);
        bindingAdd.botonAdd.setOnClickListener(this);
        bindingAdd.botonBorrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v== bindingAdd.botonAdd){
            crearReserva();
        }
        if (v == bindingAdd.addfechaPaseo) {
            mostrarCalendario();
        }
        if(v== bindingAdd.addhoraPaseo){
            obtenerHora();
        }
        if(v== bindingAdd.botonBorrar){
            finish();
        }
    }

    private void crearReserva() {
        bindingAdd.addnombreJinete.setError(null);
        bindingAdd.addfechaPaseo.setError(null);
        bindingAdd.addcaballo.setError(null);
        bindingAdd.addtelefono.setError(null);
        String jinete = bindingAdd.addnombreJinete.getText().toString();
        String fecha = bindingAdd.addfechaPaseo.getText().toString();
        String nombreCaballo = bindingAdd.addcaballo.getText().toString();
        String horaPAseo = bindingAdd.addhoraPaseo.getText().toString();
        String telefonoMovil = bindingAdd.addtelefono.getText().toString();
        String coment = bindingAdd.addcomentario.getText().toString();
            if ("".equals(jinete)) {
                bindingAdd.addnombreJinete.setText("Tienes que introducir el dato");
                bindingAdd.addnombreJinete.requestFocus();
                return;
            }
            if ("".equals(fecha)) {
                bindingAdd.addfechaPaseo.setText("Tienes que introducir el dato");
                bindingAdd.addfechaPaseo.requestFocus();
                return;
            }
            if ("".equals(nombreCaballo)) {
                bindingAdd.addcaballo.setText("Tienes que introducir el dato");
                bindingAdd.addcaballo.requestFocus();
                return;
            }
            if ("".equals(horaPAseo)) {
                bindingAdd.addhoraPaseo.setText("Tienes que introducir el dato");
                bindingAdd.addhoraPaseo.requestFocus();
                return;
            }
            if ("".equals(telefonoMovil)) {
                bindingAdd.addtelefono.setText("Tienes que introducir el dato");
                bindingAdd.addtelefono.requestFocus();
                return;
            }
            if (telefonoMovil.length() != 9) {
                Toast.makeText(AddReserva.this, "El telefono tiene que tener 9 digitos",
                        Toast.LENGTH_SHORT).show();
                bindingAdd.addtelefono.requestFocus();
                return;
            }
            Intent datoAdd=new Intent();
            datoAdd.putExtra(EXTRA_ADDJINETE,jinete);
            datoAdd.putExtra(EXTRA_ADDFECHA,fecha);
            datoAdd.putExtra(EXTRA_ADDCABALLO,nombreCaballo);
            datoAdd.putExtra(EXTRA_ADDHORA,horaPAseo);
            datoAdd.putExtra(EXTRA_ADDTELEFONO,telefonoMovil);
            datoAdd.putExtra(EXTRA_ADDCOMENTARIO,coment);
            setResult(RESULT_OK,datoAdd);
            finish();
        }
//Muestra el Datepicker y lo pasa  a una editText
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
                bindingAdd.addfechaPaseo.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    //Muestra el TimerPicker, filtra las horas  lo pasa  a una editText
    private void obtenerHora() {

            TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    view.setIs24HourView(true);
                    String horaFormateada = "";
                    if((hourOfDay<17)||(hourOfDay>20)) {
                        Toast.makeText(AddReserva.this,"Los paseos empiezan a 17:00 y el último es a las 20:00.",Toast.LENGTH_SHORT).show();
                        horaFormateada= "17";
                    }else{
                        horaFormateada=String.valueOf(hourOfDay);
                    }
                    //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                    String minutoFormateado = "";
                    if(minute != 0){
                        Toast.makeText(AddReserva.this,"Los paseos empiezan en las horas en punto, se pondra en punto .",Toast.LENGTH_SHORT).show();
                        minutoFormateado="0";
                    }else{
                        minutoFormateado=String.valueOf(minute);
                    }


                    //Muestro la hora con el formato deseado
                    bindingAdd.addhoraPaseo.setText(horaFormateada + ":0" + minutoFormateado );
                }
                //Estos valores deben ir en ese orden
                //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
                //Pero el sistema devuelve la hora en formato 24 horas
            }, hora, minuto, true);
            recogerHora.show();
        }

}