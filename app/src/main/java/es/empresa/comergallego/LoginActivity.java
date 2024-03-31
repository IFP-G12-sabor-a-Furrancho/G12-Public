package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    protected TextInputEditText caja1;
    protected TextInputEditText caja2;
    protected ImageView ima1;
    protected Button button1;
    protected Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        caja1 = (TextInputEditText) findViewById(R.id.text1_register);
        caja2 = (TextInputEditText) findViewById(R.id.text2_register);
        ima1= (ImageView) findViewById(R.id.ima1_login);
        button1 = (Button) findViewById(R.id.button1_login);
        button2 = (Button) findViewById(R.id.boton2_login);
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