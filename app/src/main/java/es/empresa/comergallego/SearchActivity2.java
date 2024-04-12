/*package es.empresa.comergallego;

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
import android.widget.SearchView;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener {

    protected SearchView search1;
    protected ListView lista1;
    private ArrayList<Local> locales = new ArrayList<>();
    private ArrayAdapter<Local> adaptador;
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

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Local localSeleccionado = locales.get(position);
                Intent intent = new Intent(SearchActivity.this, DetalleLocalActivity.class);
                intent.putExtra("localDetalles", localSeleccionado); // Asegúrate de que Local implementa Serializable
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            GestorBBDOperacionesLocales bdLocales = new GestorBBDOperacionesLocales();
            locales = bdLocales.consulta(query); // Asume que consulta ha sido ajustada para devolver ArrayList<Local>
            adaptador = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, locales);
            lista1.setAdapter(adaptador);
        } catch (SQLException e) {
            Toast.makeText(SearchActivity.this, "Error al realizar la búsqueda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
        inflater.inflate(R.menu.menu_search, menu); // Asegúrate de tener definido menu_search.xml en res/menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_locales:
                Intent intentListadoLocales = new Intent(SearchActivity.this, ListadoLocalesPropios.class);
                intentListadoLocales.putExtra("NOMBREUSUARIO", paquete);
                startActivity(intentListadoLocales);
                return true;
            case R.id.item_menu_usuario:
                Intent intentEditarUsuario = new Intent(SearchActivity.this, EditActivity.class); // Asumiendo que tienes una EditActivity
                intentEditarUsuario.putExtra("NOMBREUSUARIO", paquete);
                startActivity(intentEditarUsuario);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuLocales = menu.findItem(R.id.item_menu_locales);
        extras = getIntent().getExtras();
        if (extras != null) {
            paquete = extras.getString("NOMBREUSUARIO");
            try {
                // Aquí deberías tener una instancia de GestorBBDDOperacionesUsuarios y verificar si el usuario es administrador
                String rol = "false"; // Este valor debe obtenerse de la verificación real
                menuLocales.setVisible(rol.equals("true"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}*/
