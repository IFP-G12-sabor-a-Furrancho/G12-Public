package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    protected TextInputEditText caja1;
    protected TextInputEditText caja2;
    protected ImageView ima1;
    protected Button button1;
    protected Button button2;
    private GestorBBDD bd;


    private Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        caja1 = (TextInputEditText) findViewById(R.id.text1_register);
        caja2 = (TextInputEditText) findViewById(R.id.text2_register);
        ima1= (ImageView) findViewById(R.id.ima1_login);
        button1 = (Button) findViewById(R.id.button1_login);
        button2 = (Button) findViewById(R.id.boton2_login);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    bd = new GestorBBDD();
                    boolean loginExitoso = GestorBBDDOperacionesUsuarios.consultarLogin(caja1.getText().toString().toLowerCase(),caja2.getText().toString(), bd);
                    if (loginExitoso){
                        Toast.makeText(LoginActivity.this, "Login Exitoso", Toast.LENGTH_SHORT).show();

                        bd.desconectarBBDD();
                        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                        pasarPantalla.putExtra("NOMBREUSUARIO",caja1.getText().toString());
                        //Intent intent = new Intent(StartActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        bd.desconectarBBDD();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //Intent intent = new Intent(StartActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.item_salir_login:
               System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}