package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
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
    protected ListView lista1;
    private ArrayList<String> localizaciones = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;

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
}