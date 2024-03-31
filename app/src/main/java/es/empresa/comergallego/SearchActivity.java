package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    protected SearchView search1;
    protected GestorBBDD bd;
    protected TextView caja1;

    String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    String user = "fl0user";
    String password = "8Zizcvy1rMhs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        search1 = (SearchView) findViewById(R.id.search1_search);
        caja1 = (TextView) findViewById(R.id.caja1_search);

        search1.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Toast.makeText(SearchActivity.this, "Exito", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            GestorBBDOperacionesLocales bdLocales = new GestorBBDOperacionesLocales();

            caja1.setText(bdLocales.consulta(query));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}