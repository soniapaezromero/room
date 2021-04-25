package com.example.paez_sonia_room.Hipica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.paez_sonia_room.Hipica.adaptadores.RecyclerTouchListener;
import com.example.paez_sonia_room.Hipica.adaptadores.ReservasAdapter;
import com.example.paez_sonia_room.Hipica.busqueda.Buscador;
import com.example.paez_sonia_room.Hipica.database.Reserva;
import com.example.paez_sonia_room.Hipica.database.ReservaRepositorio;
import com.example.paez_sonia_room.Hipica.database.add.AddReserva;
import com.example.paez_sonia_room.Hipica.database.edit.EditReserva;
import com.example.paez_sonia_room.R;
import com.example.paez_sonia_room.databinding.ActivityMainHipicaBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad Principal
 */

public class MainActivityHipica extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainHipicaBinding bindingHipica;
    private ReservaRepositorio repositorio;
    private Reserva reserva;
    private ReservasAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel mainViewModel;
    public final int RESERVAS_MAX =2;
    boolean reservaExistente= false;
    public int paseosReservados=0;
    public String fecha;
    public String horaPAseo;
    public  List<Reserva> listaPaseos;
    public static int ADDRESERVAREQUEST=1;
    public static int EDITRESERVAREQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hipica);
        bindingHipica =ActivityMainHipicaBinding.inflate(getLayoutInflater());
        View view = bindingHipica.getRoot();
        setContentView(view);
    //Cargo el RecyclerView
    adapter= new ReservasAdapter();
    bindingHipica.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    bindingHipica.recyclerView.setHasFixedSize(true);
    bindingHipica.recyclerView.setAdapter(adapter);
    //LLamo al View model
    mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
    mainViewModel.getAllReservas().observe(this, new Observer<List<Reserva>>() {
        @Override
        public void onChanged(List<Reserva> reservas) {
                adapter.setPaseos(reservas);
                setListaPaseos(reservas);
            }

        });

         bindingHipica.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, bindingHipica.recyclerView, new RecyclerTouchListener.ClickListener() {
             //Click Corto modifica la Reserva
             @Override
             public void onClick(View view, int position) {
                 Reserva reservaEditada=listaPaseos.get(position);
                 AlertDialog alertDialog=new AlertDialog.Builder(MainActivityHipica.this).setPositiveButton("Sí, Cambiar", new DialogInterface.OnClickListener() {
                     /**
                      * This method will be invoked when a button in the dialog is clicked.
                      *
                      * @param dialog the dialog that received the click
                      * @param which  the button that was clicked (ex.
                      *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                      */
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Intent intent = new Intent(MainActivityHipica.this, EditReserva.class);
                         intent.putExtra(EditReserva.EXTRA_EDITID, reservaEditada.getIdpaseo());
                         intent.putExtra(EditReserva.EXTRA_EDITJINETE, reservaEditada.getNombreJinete());
                         intent.putExtra(EditReserva.EXTRA_EDITFECHA, reservaEditada.getFechaPaseo());
                         intent.putExtra(EditReserva.EXTRA_EDITCABALLO, reservaEditada.getNombreCaballo());
                         intent.putExtra(EditReserva.EXTRA_EDITHORA, reservaEditada.getHoraPaseo());
                         intent.putExtra(EditReserva.EXTRA_EDITELEFONO, reservaEditada.getTelefono());
                         intent.putExtra(EditReserva.EXTRA_EDITCOMENTARIO, reservaEditada.getComentario());
                         startActivityForResult(intent, EDITRESERVAREQUEST);
                     }
                 }).setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 })
                         .setTitle("Confirmar")
                         .setMessage("¿Modificar a la el paseo  del dia " + reservaEditada.getFechaPaseo() +"  a la hora "+reservaEditada.getHoraPaseo()+ "?")
                         .create();
                 alertDialog.show();
             }
             //Click largo elimina la Reserva
             @Override
             public void onLongClick(View view, int position) {
                 final Reserva paseoEliminar=listaPaseos.get(position);;
                 AlertDialog dialog1 = new AlertDialog
                         .Builder(MainActivityHipica.this)
                         .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                mainViewModel.Delete(paseoEliminar);
                                refrescarReservas();

                                Toast.makeText(MainActivityHipica.this, "Paseo Eliminado", Toast.LENGTH_SHORT).show();

                             }
                         })
                         .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                             }
                         })
                         .setTitle("Confirmar")
                         .setMessage("¿Eliminar a la el paseo  del dia " + paseoEliminar.getFechaPaseo() +"  a la hora "+paseoEliminar.getHoraPaseo()+ "?")
                         .create();
                 dialog1.show();

             }


        }));

    //Actualizamos el recyclerView
    refrescarReservas();

    bindingHipica.fab.setOnClickListener(this);
}
    //Creamos el Menu donde hemos puesto la busqueda de REseva
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }
    // El icono Abre el fomulario de busqueda
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.buscar:
                    Intent intentoBusqueda= new Intent(MainActivityHipica.this, Buscador.class);
                    startActivity(intentoBusqueda);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Si damos al botón flotante
    @Override
    public void onClick(View v) {
        if(v== bindingHipica.fab){
            Intent intento = new Intent(MainActivityHipica.this, AddReserva.class);
            startActivityForResult(intento,ADDRESERVAREQUEST);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        refrescarReservas();
    }
    //actuliza recycler view
    public void refrescarReservas() {
        if (adapter == null) return;
        List<Reserva> paseos= getListaPaseos();
        adapter.setPaseos(paseos);
        adapter.notifyDataSetChanged();
    }
    private int contarpaseos() {
        paseosReservados++;
        int paseos=paseosReservados++;
        setPaseosReservados(paseos);
        return paseos;
    }
// Método para comprobar si existe Ese PAseo Reservado
    private boolean comprobarReserva() {
        boolean existe = false;
        String fecha =getFecha();
        String horaPAseo = getHoraPAseo();
        List<Reserva> listReserva=getListaPaseos();
        for (Reserva r : listReserva) {
            if ((r.getFechaPaseo().equals(fecha)) && (r.getHoraPaseo().equals(horaPAseo))) {
                existe = true;
                contarpaseos();
                Log.e("Comprobar:","comporobado paseo" );
            }
        }
        return existe;
    }

    public int getPaseosReservados() {
        return paseosReservados;
    }

    public void setPaseosReservados(int paseosReservados) {
        this.paseosReservados = paseosReservados;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraPAseo() {
        return horaPAseo;
    }

    public void setHoraPAseo(String horaPAseo) {
        this.horaPAseo = horaPAseo;
    }

    public List<Reserva> getListaPaseos() {
        return listaPaseos;
    }

    public void setListaPaseos(List<Reserva> listaPaseos) {
        this.listaPaseos = listaPaseos;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resibimos datos de la Clase ADD Reserva
        if(requestCode== ADDRESERVAREQUEST && resultCode == RESULT_OK) {// Si sale bien el intent recogemos los datos
            String jineteAdd = data.getStringExtra(AddReserva.EXTRA_ADDJINETE);
            String fechaAdd = data.getStringExtra(AddReserva.EXTRA_ADDFECHA);
            setFecha(fechaAdd);
            String caballoAdd = data.getStringExtra(AddReserva.EXTRA_ADDCABALLO);
            String horaAdd = data.getStringExtra(AddReserva.EXTRA_ADDHORA);
            String telefonoAdd = data.getStringExtra(AddReserva.EXTRA_ADDTELEFONO);
            String comentarioAdd = data.getStringExtra(AddReserva.EXTRA_ADDCOMENTARIO);
            reserva = new Reserva();
            reserva.setNombreJinete(jineteAdd);
            reserva.setFechaPaseo(fechaAdd);
            reserva.setNombreCaballo(caballoAdd);
            reserva.setHoraPaseo(horaAdd);
            reserva.setTelefono(telefonoAdd);
            reserva.setComentario(comentarioAdd);
            comprobarReserva();// Comprobamos si existe ya esa fecha en los apseos reservado
            paseosReservados=contarpaseos();
            if (paseosReservados <= RESERVAS_MAX) {// Si no supera el numero mximo de resrvas
                mainViewModel.Insert(reserva);//Insertamos los datos de la base de datos
                Toast.makeText(this, "Paseo reservado",
                        Toast.LENGTH_SHORT).show();
                //Mandamos el whassap del confimado
                String telefono="34"+telefonoAdd;
                String mensaje= jineteAdd+" tiene un paseo reservado  con el caballo: "+caballoAdd+" el dia: "+fechaAdd+" a la hora:" +horaAdd;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=" +telefono + "&text=" + mensaje;
                intent.setData(Uri.parse(uri));
                try {
                    startActivity(intent);
                    Toast.makeText(this, "Mensaje de confirmacion mandado por WhatsApp", Toast.LENGTH_LONG)
                            .show();
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(this, "El dispositivo no tiene instalado WhatsApp", Toast.LENGTH_LONG)
                            .show();
                }
            } else {// Si estan todos las reservas hechas manda mensaje
                Toast.makeText(this, "Todos los  paseos ya esta reservados",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        }else if (requestCode== EDITRESERVAREQUEST && resultCode == RESULT_OK) {// confirmamos los datos de lamodificacion y si lo cumple modificamoe el registro
            Integer id= data.getIntExtra(EditReserva.EXTRA_EDITID,-1);
            if(id==-1){
                Toast.makeText(this, "Paseo no se puede modificar",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String jineteEdit = data.getStringExtra(EditReserva.EXTRA_EDITJINETE);
            Log.e("JINETEm", jineteEdit);
            String fechaEdit = data.getStringExtra(EditReserva.EXTRA_EDITFECHA);
            Log.e("FECHAM", fechaEdit);
            setFecha(fechaEdit);
            String caballoEdit = data.getStringExtra(EditReserva.EXTRA_EDITCABALLO);
            Log.e("CABAM", caballoEdit);
            String horaEdit = data.getStringExtra(EditReserva.EXTRA_EDITHORA);
            Log.e("HORAAÑ", horaEdit);
            setHoraPAseo(horaEdit);
            String telefonoEdit = data.getStringExtra(EditReserva.EXTRA_EDITELEFONO);
            Log.e("TELAM", telefonoEdit);
            String comentarioEdit= data.getStringExtra(EditReserva.EXTRA_EDITCOMENTARIO);
            Log.e("COMEM", comentarioEdit);
            reserva = new Reserva();
            reserva.setIdpaseo(id);
            reserva.setNombreJinete(jineteEdit);
            reserva.setFechaPaseo(fechaEdit);
            reserva.setNombreCaballo(caballoEdit);
            reserva.setHoraPaseo(horaEdit);
            reserva.setTelefono(telefonoEdit);
            reserva.setComentario(comentarioEdit);
            comprobarReserva();
            paseosReservados=contarpaseos();
            if (paseosReservados <= RESERVAS_MAX) {
                mainViewModel.Update(reserva);
                Toast.makeText(this, "Paseo modificado",
                        Toast.LENGTH_SHORT).show();
                String telefono="34"+telefonoEdit;
                String mensaje= jineteEdit+" amodificado su  paseo reservado  con el caballo: "+caballoEdit+" el dia: "+fechaEdit+" a la hora:" +horaEdit;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=" +telefono + "&text=" + mensaje;
                intent.setData(Uri.parse(uri));

                try {
                    startActivity(intent);
                    Toast.makeText(this, "Mensaje de confirmacion mandado por WhatsApp", Toast.LENGTH_LONG)
                            .show();
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(this, "El dispositivo no tiene instalado WhatsApp", Toast.LENGTH_LONG)
                            .show();
                }

            } else {
                Toast.makeText(this, "Todos los  paseos ya esta reservados",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        }else{
            Toast.makeText(this, "Paseo no reservado",
                    Toast.LENGTH_SHORT).show();
        }
    }

}