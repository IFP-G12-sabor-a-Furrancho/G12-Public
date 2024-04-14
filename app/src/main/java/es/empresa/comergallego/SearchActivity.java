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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    protected SearchView search1;
    protected ListView lista1;
    private ArrayAdapter<String> adaptador;
    private Intent pasarPantalla;
    private Bundle extras;
    private String paquete = "";

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
            GestorBBDOperacionesLocales bdLocales = new GestorBBDOperacionesLocales();
            ArrayList<String> localizaciones = bdLocales.consulta(query);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, localizaciones);
            lista1.setAdapter(adaptador);
            Toast.makeText(this, "Búsqueda completada.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error en la búsqueda.", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()) {
            case R.id.item_menu_locales:
                Intent intent = new Intent(this, ListadoLocalesPropios.class);
                intent.putExtra("NOMBREUSUARIO", paquete);
                startActivity(intent);
                finish();
                return true;
            case R.id.item_menu_usuario:
                pasarPantalla = new Intent(this, EditActivity.class);
                pasarPantalla.putExtra("NOMBREUSUARIO", paquete);
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
        if (extras != null) {
            try {
                paquete = extras.getString("NOMBREUSUARIO");
                menuLocales.setVisible(true);  // Aquí podrías manejar si se muestra o no según el rol del usuario
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
