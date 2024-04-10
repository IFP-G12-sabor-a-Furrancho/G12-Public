package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    protected SearchView search1;
    protected GestorBBDD bd;
    protected GestorBBDDOperacionesUsuarios gestorUsuarios;
    protected ListView lista1;
    private ArrayList<String> localizaciones = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;
    private Intent pasarPantalla;
    private Bundle extras;
    private String paquete="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        search1 = (SearchView) findViewById(R.id.search1_search);
        lista1 = (ListView) findViewById(R.id.lista1_search);
        search1.setOnQueryTextListener(this);


    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        try {


            //bd = new GestorBBDD();

            Toast.makeText(SearchActivity.this, "Exito", Toast.LENGTH_SHORT).show();

            GestorBBDOperacionesLocales bdLocales = new GestorBBDOperacionesLocales();

            localizaciones = bdLocales.consulta(query);
            adaptador = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, localizaciones);
            lista1.setAdapter(adaptador);



            //bd.desconectarBBDD();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.item_menu_locales:
                Intent intent = new Intent(SearchActivity.this, ListadoLocalesPropios.class);
                intent.putExtra("NOMBREUSUARIO",paquete);
                startActivity(intent);
                finish();


                finish();
                return true;
            case R.id.item_menu_usuario:
                Intent pasarPantalla = new Intent(SearchActivity.this, EditActivity.class);
                pasarPantalla.putExtra("NOMBREUSUARIO",paquete);
                startActivity(pasarPantalla);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuLocales = menu.findItem(R.id.item_menu_locales);

        extras = getIntent().getExtras();

        if (extras!=null){

            try {
                paquete =extras.getString("NOMBREUSUARIO");
                gestorUsuarios = new GestorBBDDOperacionesUsuarios();
                String rol = gestorUsuarios.consultaAdministrador(paquete);
                if (rol.equals("true")) {
                    menuLocales.setVisible(true);
                } else {
                    menuLocales.setVisible(false);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}