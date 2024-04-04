package es.ifp.proyectodamalberto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Activity3B extends AppCompatActivity {

    //Definición de atributos
    private ListView lista1;

    private ArrayList<String> nombres;
    private ArrayAdapter<String> adaptador = null;
    private boolean crear=false;
    GestorBBDDOperacionesLocales bbddlocales;
    final String url = "jdbc:postgresql://ep-nameless-snow-71296629.eu-central-1.aws.neon.fl0.io:5432/comergallego-Alberto?sslmode=require";
    String usuario = "fl0user";
    String contrasena = "lpEWc0JdMgK4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3_b);

        //Referenciamos componentes
        lista1 = (ListView) findViewById(R.id.lista1_Activity3b);

        // Creamos una instancia de GestorBBDDOperacionesLocales
        try {
            bbddlocales = new GestorBBDDOperacionesLocales(url, usuario, contrasena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //Obtenemos los nombres de la base de datos y las almacenamos en el ArrayList nombres

        //Revisar el statement
        try {
            nombres = bbddlocales.getNombresLocales();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Creamos y Asignamos el ArrayLlist al adaptador
        adaptador = new ArrayAdapter<String>(Activity3B.this, android.R.layout.simple_list_item_1, nombres);
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

                Intent pasarPantalla = new Intent(Activity3B.this, Activity3C.class);
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
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Codigo de las opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CrearLocal_menu:
                //Pasamos a la actividad 3C para crear un nuevo local
                Intent pasarPantalla = new Intent(Activity3B.this, Activity3C.class);
                //En el caso de que deseemos crear un nuevo local, la actividad 3C se deberá de mostrar vacía
                Boolean crear=true;
                pasarPantalla.putExtra("crear", crear);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}