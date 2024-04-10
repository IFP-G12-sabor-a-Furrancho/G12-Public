package es.empresa.comergallego;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_local);

        // Recuperar el objeto Local del Intent
        Local localDetalles = (Local) getIntent().getSerializableExtra("localDetalles");

        // Inicializar vistas
        TextView textViewDetalles = findViewById(R.id.textViewDetalles);

        // Mostrar los detalles del local
        if (localDetalles != null) {
            String detalles = "ID: " + localDetalles.getId() + "\n" +
                    "Nombre: " + localDetalles.getNombre() + "\n" +
                    "Dirección: " + localDetalles.getDireccion() + "\n" +
                    "Teléfono: " + localDetalles.getTelefono();
            textViewDetalles.setText(detalles);
        }
    }
}
