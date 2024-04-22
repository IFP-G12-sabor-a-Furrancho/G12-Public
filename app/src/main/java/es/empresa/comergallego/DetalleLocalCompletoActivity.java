package es.empresa.comergallego;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import es.empresa.comergallego.Local;
import es.empresa.comergallego.R;

public class DetalleLocalCompletoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_local_completo);

        // Recuperar el objeto Local del Intent
        Local localDetalles = (Local) getIntent().getSerializableExtra("localDetalles");

        // Verificar que el objeto no sea null
        if (localDetalles != null) {
            // Asignar valores a los TextViews
            ((TextView) findViewById(R.id.tvNombreLocal)).setText(localDetalles.getNombre());
            ((TextView) findViewById(R.id.tvDireccion)).setText(localDetalles.getDireccion());
            ((TextView) findViewById(R.id.tvDescripcion)).setText(localDetalles.getDescripcion());
            ((TextView) findViewById(R.id.tvTipoLocal)).setText(localDetalles.getTipoLocal());
            ((TextView) findViewById(R.id.tvHorario)).setText(localDetalles.getHorario());
            ((TextView) findViewById(R.id.tvTelefono)).setText(localDetalles.getTelefono());
            ((TextView) findViewById(R.id.tvCoordenadasGPS)).setText(localDetalles.getCoordenadasGPS());
        }
    }
}

