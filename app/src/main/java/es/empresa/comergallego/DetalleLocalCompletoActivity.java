package es.empresa.comergallego;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleLocalCompletoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_local_completo);

        // Recuperar el objeto Local del Intent
        Local localDetalles = (Local) getIntent().getSerializableExtra("local");

        // Asignar valores a las vistas
        TextView tvNombreLocal = findViewById(R.id.tvNombreLocal);
        TextView tvDireccion = findViewById(R.id.tvDireccion);
        TextView tvDescripcion = findViewById(R.id.tvDescripcion);
        TextView tvTipoLocal = findViewById(R.id.tvTipoLocal);
        TextView tvHorario = findViewById(R.id.tvHorario);
        TextView tvTelefono = findViewById(R.id.tvTelefono);
        TextView tvCoordenadasGPS = findViewById(R.id.tvCoordenadasGPS);

        // Configurar vistas
        tvNombreLocal.setText(localDetalles.getNombre());
        tvDireccion.setText(localDetalles.getDireccion());
        tvDescripcion.setText(localDetalles.getDescripcion());
        tvTipoLocal.setText(localDetalles.getTipoLocal());
        tvHorario.setText(localDetalles.getHorario());
        tvTelefono.setText(localDetalles.getTelefono());
        tvCoordenadasGPS.setText(localDetalles.getCoordenadasGPS());
    }
}
