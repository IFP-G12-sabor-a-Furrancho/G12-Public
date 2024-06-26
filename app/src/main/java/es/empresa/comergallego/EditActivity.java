package es.empresa.comergallego;

import static es.empresa.comergallego.RegisterActivity.validarContraseña;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EditActivity extends AppCompatActivity {

    EditText editTextNombre, editTextApellido, editTextCorreo, editTextContrasenaActual, editTextNuevaContrasena;
    Button btnGuardarCambios, btnAtras;

    ImageView image;
    String url = "jdbc:postgresql://ep-proud-queen-a25i44xx.eu-central-1.aws.neon.tech/comergallego";
    String user = "comergallego_owner";
    String password = "MnexL8Y1OZCc";
    private String[] datosUsuarioArray;

    private Bundle extras;
    private String idUsuario;
    private String paquete;
    private GestorBBDDOperacionesUsuarios bbddUsuarios;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasenaActual = findViewById(R.id.editTextContraseñaActual);
        editTextNuevaContrasena = findViewById(R.id.editTextNuevaContraseña);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnAtras = findViewById(R.id.boton2_edit);
        image = findViewById(R.id.image1_edit);

        try {
            bbddUsuarios = new GestorBBDDOperacionesUsuarios();
            extras = getIntent().getExtras();
            if (extras != null) {
                paquete = extras.getString("NOMBREUSUARIO");
                idUsuario = bbddUsuarios.consultaID(paquete);
                String consulta = bbddUsuarios.usuario(idUsuario);

                datosUsuarioArray = consulta.split("-");

                editTextNombre.setText(datosUsuarioArray[0]);
                editTextApellido.setText(datosUsuarioArray[1]);
                editTextCorreo.setText(datosUsuarioArray[2]);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Los requisitos mínimos son los siguientes: \n 1. Longitud mínima de 8 carácteres \n 2. Uno de los carácteres como mínimo ha de ser:\n - Un número \n - Una letra minúscula\n - Una letra mayúscula\n - Un carácter especial (!@#$%^&*()-+)", Snackbar.LENGTH_INDEFINITE);
                View snackbarView = snackbar.getView();
                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                // Ajustar el ancho del texto para mostrar todo el contenido
                textView.setMaxLines(5); // Esto asegura que el texto tenga suficiente espacio para mostrar todas las líneas
                textView.setSingleLine(false); // Permite múltiples líneas de texto
                // Ajustar la gravedad del texto para que esté centrado
                textView.setGravity(Gravity.CENTER_HORIZONTAL);

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int screenHeight = metrics.heightPixels;
                int yOffset = (int) (screenHeight * 0.385);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                params.topMargin = yOffset;
                //params.height = 80;
                snackbarView.setLayoutParams(params);
                snackbar.setDuration(10000);
                snackbar.show();

            }
        });


        // Listener para el botón de guardar cambios
        btnGuardarCambios.setOnClickListener(v -> {

            if (!editTextNuevaContrasena.getText().toString().isEmpty() && !editTextContrasenaActual.getText().toString().isEmpty())
            {
                actualizarDatosUsuario();

                boolean requisitosMinimos= validarContraseña(editTextNuevaContrasena.getText().toString());
                if (!requisitosMinimos)
                {
                    Toast.makeText(EditActivity.this, "La contraseña no cumple los requisitos mínimos de seguridad", Toast.LENGTH_LONG).show();
                    editTextNuevaContrasena.setText("");
                }

                else
                {
                    cambiarContrasena();
                }
            }

            else if (editTextNuevaContrasena.getText().toString().isEmpty() && editTextContrasenaActual.getText().toString().isEmpty())
            {
                actualizarDatosUsuario();
            }

            else if (editTextNuevaContrasena.getText().toString().isEmpty() || editTextContrasenaActual.getText().toString().isEmpty())
            {
                actualizarDatosUsuario();
                Toast.makeText(EditActivity.this, "Alguno de los campos de la contraseña se halla vacío.", Toast.LENGTH_SHORT).show();
            }
        });

        btnAtras.setOnClickListener(v -> {
            Intent pasarPantalla = new Intent(EditActivity.this, SearchActivity.class);
            pasarPantalla.putExtra("NOMBREUSUARIO", paquete);
            startActivity(pasarPantalla);
            finish();
        });
    }

    private void actualizarDatosUsuario() {
        String nuevoNombre = editTextNombre.getText().toString();
        String nuevoApellido = editTextApellido.getText().toString();
        String nuevoCorreo = editTextCorreo.getText().toString();

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            bbddUsuarios.actualizarNombre(idUsuario, nuevoNombre, statement);
            bbddUsuarios.actualizarApellido(idUsuario, nuevoApellido, statement);
            bbddUsuarios.actualizarCorreoElectronico(idUsuario, nuevoCorreo, statement);

            statement.close();
            connection.close();
            Toast.makeText(EditActivity.this, "Datos del usuario actualizados correctamente.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(EditActivity.this, "Error al actualizar datos del usuario.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cambiarContrasena() {

        String contrasenaActual = editTextContrasenaActual.getText().toString();
        String nuevaContrasena = editTextNuevaContrasena.getText().toString();

        new Thread(() -> {
            try {
                boolean success = bbddUsuarios.modificarContrasena(idUsuario, contrasenaActual, nuevaContrasena);
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(EditActivity.this, "Contraseña actualizada correctamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditActivity.this, "Error: La contraseña actual no es correcta.", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (SQLException e) {
                runOnUiThread(() -> Toast.makeText(EditActivity.this, "Error al actualizar la contraseña: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}
