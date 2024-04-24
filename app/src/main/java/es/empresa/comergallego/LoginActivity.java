package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    protected TextInputEditText caja1;
    protected TextInputEditText caja2;
    protected ImageView ima1;
    protected Button button1;
    protected Button button2;
    protected ProgressBar progressBar;
    private GestorBBDD bd;

    private Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        caja1 = findViewById(R.id.text1_register);
        caja2 = findViewById(R.id.text2_register);
        ima1 = findViewById(R.id.ima1_login);
        button1 = findViewById(R.id.button1_login);
        button2 = findViewById(R.id.boton2_login);
        progressBar = findViewById(R.id.progress_bar_login);
        progressBar.setVisibility(View.GONE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new LoginTask().execute(caja1.getText().toString(), caja2.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String password;

        @Override
        protected Boolean doInBackground(String... strings) {
            username = strings[0].toLowerCase();
            password = strings[1];

            try {
                bd = new GestorBBDD();
                String hashedPass = RegisterActivity.hashPassword(password);
                return GestorBBDDOperacionesUsuarios.consultarLogin(username, hashedPass, bd);
            } catch (SQLException e) {
                Log.e("LoginActivity", "Error al conectar con la base de datos", e);
                return null;
            } finally {
                if (bd != null) {
                    bd.desconectarBBDD();
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean loginExitoso) {
            progressBar.setVisibility(View.GONE);
            if (loginExitoso == null) {
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            } else if (loginExitoso) {
                Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                pasarPantalla = new Intent(LoginActivity.this, SearchActivity.class);
                pasarPantalla.putExtra("NOMBREUSUARIO", username);
                startActivity(pasarPantalla);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_salir_login) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
