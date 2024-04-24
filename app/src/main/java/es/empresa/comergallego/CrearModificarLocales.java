package es.empresa.comergallego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class CrearModificarLocales extends AppCompatActivity {

    private boolean crear;
    private int id;
    private Bundle extras;
    private String datosLocal = "";
    private String nombreLocal = "";
    private String direccion = "";
    private String descripcion = "";
    private String tipoLocal = "";
    private String horario = "";
    private String telefono = "";
    private String coordenadasGPS = "";
    private String[] datosLocalArray;

    private TextView label1;
    private EditText caja1;
    private EditText caja2;
    private EditText caja3;
    private EditText caja4;
    private EditText caja5;
    private EditText caja6;
    private EditText caja7;
    private Button boton1;
    private Button boton2;
    private int idUser=0;
    GestorBBDOperacionesLocales bbddlocales;
    GestorBBDDOperacionesUsuarios bbddUsuarios;
    //String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    //String user = "fl0user";
    //String password = "8Zizcvy1rMhs";

    String url = "jdbc:postgresql://ep-proud-queen-a25i44xx.eu-central-1.aws.neon.tech/comergallego";
    String user = "comergallego_owner";
    String password = "MnexL8Y1OZCc";
    private String paquete="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_local);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        //Referenciamos los componentes
        label1 = (TextView) findViewById(R.id.label1_activity3c);
        caja1 = (EditText) findViewById(R.id.caja1_activity3c);
        caja2 = (EditText) findViewById(R.id.caja2_activity3c);
        caja3 = (EditText) findViewById(R.id.caja3_activity3c);
        caja4 = (EditText) findViewById(R.id.caja4_activity3c);
        caja5 = (EditText) findViewById(R.id.caja5_activity3c);
        caja6 = (EditText) findViewById(R.id.caja6_activity3c);
        caja7 = (EditText) findViewById(R.id.caja7_activity3c);
        boton1 = (Button) findViewById(R.id.boton1_activity3c);
        boton2 = (Button) findViewById(R.id.boton2_activity3c);

        // Creamos una instancia de GestorBBDDOperacionesLocales

        //Constructor vacío no necesita (de momento) try y catch

        try {
            bbddlocales = new GestorBBDOperacionesLocales();
            bbddUsuarios = new GestorBBDDOperacionesUsuarios();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Recibimos los paquetes enviados desde la actividad anterior
        extras = getIntent().getExtras();
        if (extras!=null) {
            paquete = extras.getString("NOMBREUSUARIO");
            crear = extras.getBoolean("crear");
            id = extras.getInt("id");
        }


        if (crear) {
            // Código para crear un local

            //El usuario se encuentra con las cajas de texto rellenas con los siguientes Hint
            label1.setText("Crear un local");
            caja1.setHint("Nombre local");
            caja2.setHint("Direccion");
            caja3.setHint("Descripcion");
            caja4.setHint("Tipo local");
            caja5.setHint("Horario");
            caja6.setHint("Telefono");
            caja7.setHint("Coordenadas GPS");
            boton1.setText("Crear local");





            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (label1.equals("") || caja1.equals("") || caja2.equals("") || caja3.equals("") || caja4.equals("") || caja5.equals("") || caja6.equals("") || caja7.equals("")) {
                        Toast.makeText(es.empresa.comergallego.CrearModificarLocales.this, "Local creado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        nombreLocal = String.valueOf(caja1.getText());
                        direccion = String.valueOf(caja2.getText());
                        descripcion = String.valueOf(caja3.getText());
                        tipoLocal = String.valueOf(caja4.getText());
                        horario = String.valueOf(caja5.getText());
                        telefono = String.valueOf(caja6.getText());
                        coordenadasGPS = String.valueOf(caja7.getText());

                        //Ahora utilizamos el método para crear un nuevo local
                        try {
                            bbddlocales.insertarNuevoLocal(nombreLocal, direccion, descripcion, tipoLocal, horario, telefono, coordenadasGPS);

                            idUser = Integer.parseInt(bbddUsuarios.consultaID(paquete));

                            bbddlocales.insertarTablaIntermedia(idUser);

                            Toast.makeText(es.empresa.comergallego.CrearModificarLocales.this, "Local creado correctamente", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                    }


                }
            });
            boton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent pasarPantalla = new Intent(es.empresa.comergallego.CrearModificarLocales.this, Activity3B.class);
                    Intent pasarPantalla = new Intent(CrearModificarLocales.this, ListadoLocalesPropios.class);
                    pasarPantalla.putExtra("NOMBREUSUARIO",paquete);
                    startActivity(pasarPantalla);
                    finish();
                }
            });


        } else {
            // Código para modificar un local
            label1.setText("Modificar un local");
            boton1.setText("Modificar local");

            //Mediante el id, obtenemos todos los datos del local y los mostramos por pantalla, usando el método getLocales;
            try {
                datosLocal = bbddlocales.getLocales(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            datosLocalArray = datosLocal.split("-.-");

            //Empezamos obteniendo los datos desde la posicion 1 y no la 0 porque no nos interesa el ID
            nombreLocal = datosLocalArray[1];
            direccion = datosLocalArray[2];
            descripcion = datosLocalArray[3];
            tipoLocal = datosLocalArray[4];
            horario = datosLocalArray[5];
            telefono = datosLocalArray[6];
            coordenadasGPS = datosLocalArray[7];

            String[] datos = {nombreLocal, direccion, descripcion, tipoLocal, horario, telefono, coordenadasGPS};

            for (int i = 0; i < datos.length; i++) {
                if (datos[i].equals("null")) {
                    datos[i] = "No existen datos";
                }
            }


            //Mostramos los datos en las cajas de texto
            caja1.setText(datos[0]);
            caja2.setText(datos[1]);
            caja3.setText(datos[2]);
            caja4.setText(datos[3]);
            caja5.setText(datos[4]);
            caja6.setText(datos[5]);
            caja7.setText(datos[6]);
            boton1 = (Button) findViewById(R.id.boton1_activity3c);

            //Una vez mostrados los datos el usuario puede modificarlos
            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((caja1.getText().equals(nombreLocal)) && (caja2.getText().equals(direccion)) && (caja3.getText().equals(descripcion)) && (caja4.getText().equals(tipoLocal)) && (caja5.getText().equals(horario)) && (caja6.getText().equals(telefono)) && (caja7.getText().equals(coordenadasGPS))) {
                        //En el caso de que no se hayan producido cambios, se muestra un mensaje por pantalla
                        Toast.makeText(es.empresa.comergallego.CrearModificarLocales.this, "Realice algún cambio antes de continuar", Toast.LENGTH_SHORT).show();
                    } else { try {
                        //Si se ha cambiado algún dato, se comprueba que dato se ha modificado y se ejecuta la/s consulta/s correspondiente/s
                        if (!(caja1.getText().equals(nombreLocal))) {

                            bbddlocales.actualizarNombreLocal(id, caja1.getText().toString());
                        }

                        if (!(caja2.getText().equals(direccion))) {
                            bbddlocales.actualizarDireccion(id, caja2.getText().toString());
                        }
                        if (!(caja3.getText().equals(descripcion))) {
                            bbddlocales.actualizarDescripcion(id, caja3.getText().toString());
                        }
                        if (!(caja4.getText().equals(tipoLocal))) {
                            bbddlocales.actualizarTipoLocal(id, caja4.getText().toString());
                        }
                        if (!(caja5.getText().equals(horario))) {
                            bbddlocales.actualizarHorario(id, caja5.getText().toString());
                        }
                        if (!(caja6.getText().equals(telefono))) {
                            bbddlocales.actualizarTelefono(id, caja6.getText().toString());
                        }
                        if (!(caja7.getText().equals(coordenadasGPS))) {
                            bbddlocales.actualizarCoordenadasGPS(id, caja7.getText().toString());
                        }

                        Toast.makeText(es.empresa.comergallego.CrearModificarLocales.this, "Modificación realizada correctamente", Toast.LENGTH_SHORT).show();
                    }
                        catch(SQLException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            //Botón volver
            boton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent pasarPantalla = new Intent(es.empresa.comergallego.CrearModificarLocales.this, Activity3B.class);
                    Intent pasarPantalla = new Intent(CrearModificarLocales.this, ListadoLocalesPropios.class);
                    pasarPantalla.putExtra("NOMBREUSUARIO",paquete);
                    startActivity(pasarPantalla);
                    finish();
                }
            });
        }
    }
}