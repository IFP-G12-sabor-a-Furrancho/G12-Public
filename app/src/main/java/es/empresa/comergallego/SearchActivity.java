package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;

public class SearchActivity extends AppCompatActivity {

    protected Button boton1;

    protected GestorBBDD bd;
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

                try {
                    bd = new GestorBBDD();
                    bd.desconectarBBDD();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                /**String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
                String user = "fl0user";
                String password = "8Zizcvy1rMhs";

                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, user, password);
                    Toast.makeText(SearchActivity.this, "Conexión exitosa", Toast.LENGTH_SHORT).show();

                    try {


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


**/
            }
        });
    }
}