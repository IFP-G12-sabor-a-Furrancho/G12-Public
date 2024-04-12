package es.empresa.comergallego;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView search1;
    private ListView lista1;
    private ArrayList<Local> locales = new ArrayList<>();
    private ArrayAdapter<Local> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        search1 = findViewById(R.id.search1_search);
        lista1 = findViewById(R.id.lista1_search);
        search1.setOnQueryTextListener(this);

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Local localSeleccionado = locales.get(position);
                Intent intent = new Intent(SearchActivity2.this, DetalleLocalActivity.class);
                intent.putExtra("localDetalles", localSeleccionado);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            GestorBBDOperacionesLocales bdLocales = new GestorBBDOperacionesLocales();
            locales = bdLocales.consulta(query);
            adaptador = new ArrayAdapter<Local>(this, android.R.layout.simple_list_item_1, locales) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    text1.setText(locales.get(position).getNombre() + " - " + locales.get(position).getDireccion());
                    return view;
                }
            };
            lista1.setAdapter(adaptador);
        } catch (SQLException e) {
            Toast.makeText(this, "Error al realizar la búsqueda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Implementación de la lógica para manejar el cambio de texto en tiempo real, si es necesario.
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu); // Asegúrate de que este es el nombre correcto de tu menú
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja las acciones de los elementos de menú aquí
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Por ejemplo, si tienes un item de menú para ajustes podrías iniciar esa actividad aquí
            return true;
        }
        // Podrías tener otros "if" para diferentes elementos de menú aquí

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Aquí puedes modificar el menú justo antes de que se muestre, si es necesario
        return super.onPrepareOptionsMenu(menu);
    }
}
