package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchActivity extends AppCompatActivity {

    protected Button boton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        boton1 = (Button) findViewById(R.id.boton1_search);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
                String user = "fl0user";
                String password = "8Zizcvy1rMhs";

                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, user, password);
                    Toast.makeText(SearchActivity.this, "Conexión exitosa", Toast.LENGTH_SHORT).show();

                    try {
                        //String test = "INSERT INTO test (id, titulo) VALUES (?, ?)";
                        //String insertar = "INSERT INTO localizaciones (id_localizacion, nombrelocal, direccion, descripcion, tipolocal, horario, telefono, coordenadasgps, capacidad, valoracion) VALUES (?,?,?,?,?,?,?,?,?,?)";

                        String insertar = "INSERT INTO localizaciones (nombrelocal, direccion, descripcion, tipolocal, horario, telefono, coordenadasgps, capacidad, valoracion) VALUES (?,?,?,?,?,?,?,?,?)";

                        PreparedStatement pstmt = conn.prepareStatement(insertar);
                        /**pstmt.setInt(1,1);
                        pstmt.setString(2, "prueba");
                        pstmt.setString(3, "calle BCN");
                        pstmt.setString(4,"Furancho");
                        pstmt.setString(5,"Restaurante");
                        pstmt.setString(6,"12 a 15h");
                        pstmt.setString(7,"5555555");
                        pstmt.setString(8,"1234567");
                        pstmt.setInt(9,100);
                        pstmt.setString(10,"Buena");**/

                        pstmt.setString(1, "prueba2");
                        pstmt.setString(2, "calle BCN");
                        pstmt.setString(3,"Furancho");
                        pstmt.setString(4,"Restaurante");
                        pstmt.setString(5,"12 a 15h");
                        pstmt.setString(6,"5555555");
                        pstmt.setString(7,"1234567");
                        pstmt.setInt(8,100);
                        pstmt.setString(9,"Buena");

                        pstmt.executeUpdate();
                        Toast.makeText(SearchActivity.this, "Operacion realizada correctamente", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e){
                        Toast.makeText(SearchActivity.this, "Insert no realizado", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }

                    conn.close();
                } catch (SQLException e) {
                    Toast.makeText(SearchActivity.this, "Imposible conectar", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);

                }



            }
        });

    }
}