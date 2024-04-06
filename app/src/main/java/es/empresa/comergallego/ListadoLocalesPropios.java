package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListadoLocalesPropios extends AppCompatActivity {

    //Definición de atributos
    private ListView lista1;

    private ArrayList<String> nombres;
    private ArrayAdapter<String> adaptador = null;
    private boolean crear=false;
    GestorBBDOperacionesLocales bbddlocales;
    GestorBBDDOperacionesUsuarios bbddUsuarios;
    final String url = "jdbc:postgresql://ep-nameless-snow-71296629.eu-central-1.aws.neon.fl0.io:5432/comergallego-Alberto?sslmode=require";
    String usuario = "fl0user";
    String contrasena = "lpEWc0JdMgK4";
    protected Bundle extras;
    private String paquete="";
    private int idUser=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_locales);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        //Referenciamos componentes
        lista1 = (ListView) findViewById(R.id.lista1_Activity3b);

        extras = getIntent().getExtras();

        if (extras!=null) {
            paquete = extras.getString("NOMBREUSUARIO");
        }


        try {
            // Creamos una instancia de GestorBBDDOperacionesLocales
            bbddlocales = new GestorBBDOperacionesLocales();
            bbddUsuarios = new GestorBBDDOperacionesUsuarios();

            //bbddlocales = new GestorBBDOperacionesLocales();

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(ListadoLocalesPropios.this, "Error al conectar a la BBDD", Toast.LENGTH_SHORT).show();
        }

        try {
            idUser = Integer.parseInt(bbddUsuarios.consultaIDAdministrador(paquete));
            nombres = bbddlocales.getNombresLocales(idUser);
        } catch (SQLException e) {
            Toast.makeText(this, "Error al ejecutar el método getNombreLocales", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }


        //Creamos y Asignamos el ArrayLlist al adaptador
        adaptador = new ArrayAdapter<String>(ListadoLocalesPropios.this, android.R.layout.simple_list_item_1, nombres);
        //Asignamos el adaptador al ListView
        lista1.setAdapter(adaptador);

        /*Ya tenemos añadidos los elementos de la tabla al ListView de la interfaz gráfica,
         es decir, ya hemos logrado mostrar por pantalla los elementos de la tabla*/

        //Funcionamiento componentes de la interfaz / Mandar nombre del local como paquete
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Almacenamos en un String el nombre seleccionado en la lista
                String idNombreLocal = (String) nombres.get(position);

                // Extraemos solo el id y lo convertimos a Integer
                String[] partes = idNombreLocal.split("-");

                // Usamos partes[0] porque es la parte que contiene el ID
                int idLocal = Integer.parseInt(partes[0]);

                Intent pasarPantalla = new Intent(ListadoLocalesPropios.this, CrearModificarLocales.class);
                //Pasamos el id del local seleccionado como paquete
                pasarPantalla.putExtra("id", idLocal);
                // Pasamos el valor booleano como paquete para indicar que queremos modificar un local y no crearlo
                pasarPantalla.putExtra("crear", crear);

                startActivity(pasarPantalla);
                //Al pasar de pantalla, debemos de recibir el paquete con el nombre en la otra actividad, buscar en la BBDD con el id que mandamos
            }
        });
    }

    //Codigo del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_locales_propios, menu);
        return true;
    }

    //Codigo de las opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crearLocal_menu:
                //Pasamos a la actividad 3C para crear un nuevo local
                Intent pasarPantalla = new Intent(ListadoLocalesPropios.this, CrearModificarLocales.class);
                //En el caso de que deseemos crear un nuevo local, la actividad 3C se deberá de mostrar vacía
                Boolean crear=true;
                pasarPantalla.putExtra("crear", crear);
                pasarPantalla.putExtra("NOMBREUSUARIO", paquete);

                startActivity(pasarPantalla);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
