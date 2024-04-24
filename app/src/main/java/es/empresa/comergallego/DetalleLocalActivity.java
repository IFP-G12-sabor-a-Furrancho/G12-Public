package es.empresa.comergallego;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.sql.SQLException;

public class DetalleLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_local);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String localId = intent.getStringExtra("LOCAL_ID");

        try {
            GestorBBDOperacionesLocales gestor = new GestorBBDOperacionesLocales();
            Local local = gestor.getLocalDetallesById(localId);

            if (local != null) {
                ((TextView) findViewById(R.id.nombreLocal)).setText(local.getNombre());
                // Asegúrate de tener un TextView y un campo en la clase Local para cada uno de estos
                ((TextView) findViewById(R.id.direccion)).setText(local.getDireccion());
                ((TextView) findViewById(R.id.descripcion)).setText(local.getDescripcion());
                ((TextView) findViewById(R.id.tipoLocal)).setText(local.getTipoLocal());
                ((TextView) findViewById(R.id.horario)).setText(local.getHorario());
                ((TextView) findViewById(R.id.telefono)).setText(local.getTelefono());
                ((TextView) findViewById(R.id.coordenadasGPS)).setText(local.getCoordenadasGPS());
                // ... establecer el texto para los demás TextViews
            } else {
                Toast.makeText(this, "No se encontraron detalles para el local seleccionado.", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los detalles del local.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
