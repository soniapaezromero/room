package com.example.paez_sonia_room.Hipica.busqueda;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad que recoge los datos de búsqueda fecha y hora y lopasa  aotra clase
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paez_sonia_room.R;
import com.example.paez_sonia_room.databinding.ActivityBuscadorBinding;

import java.util.Calendar;

public class Buscador extends AppCompatActivity implements View.OnClickListener{

     private ActivityBuscadorBinding buscadorBinding;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        buscadorBinding = ActivityBuscadorBinding.inflate(getLayoutInflater());
        View view = buscadorBinding.getRoot();
        setContentView(view);
        setTitle("Buscador de Reservas");

        buscadorBinding.btBuscar.setOnClickListener(this);
        buscadorBinding.btonCancelar.setOnClickListener(this);
        buscadorBinding.serachFecha.setOnClickListener(this);
        buscadorBinding.serachHora.setOnClickListener(this);
    }

// LLama  a la actividad que muestra la busqueda y se cierra
    @Override
    public void onClick(View v) {
       if(v== buscadorBinding.btBuscar){
          String fecha= buscadorBinding.serachFecha.getText().toString();
          String hora =buscadorBinding.serachHora.getText().toString();
         Intent intentoListaFechas= new Intent(this,Lista_Busqueda.class);
         intentoListaFechas.putExtra("fechaBuscada",fecha);
         intentoListaFechas.putExtra("horaBuscada",hora);
         startActivity(intentoListaFechas);
         finish();
       }
       if( v == buscadorBinding.btonCancelar){
           finish();
       }
       if(v== buscadorBinding.serachFecha){
           mostrarCalendario();
       }
       if(v==buscadorBinding.serachHora){
           obtenerHora();
       }

    }

    // Muestra el datepicker y lopasa al Edit Text

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
                buscadorBinding.serachFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    //Muestra El Timer picker , filtra las horas para que esten dentro del rango y lo pasa aL Edit Text
    private void obtenerHora() {

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                view.setIs24HourView(true);
                String horaFormateada = "";
                if((hourOfDay<17)||(hourOfDay>20)) {
                    Toast.makeText(Buscador.this,"Los paseos empiezan a 17:00 y el último es a las 20:00.",Toast.LENGTH_SHORT).show();
                    horaFormateada= "17";
                }else{
                    horaFormateada=String.valueOf(hourOfDay);
                }
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = "";
                if(minute != 0){
                    Toast.makeText(Buscador.this,"Los paseos empiezan en las horas en punto, se pondra en punto .",Toast.LENGTH_SHORT).show();
                    minutoFormateado="0";
                }else{
                    minutoFormateado=String.valueOf(minute);
                }


                //Muestro la hora con el formato deseado
                buscadorBinding.serachHora.setText(horaFormateada + ":0" + minutoFormateado );
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);
        recogerHora.show();
    }

}