package es.empresa.comergallego;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    protected  EditText textName;
    protected  EditText textPass;
    protected  EditText textSurname;
    protected  EditText textData;
    protected  EditText textUserName;
    protected  EditText textEmail;

    protected TextInputLayout cajaU;
    protected ImageView image;


    private Usuarios user;

    protected Button buttonReg;
    protected GestorBBDD bd;

    private Intent pasaPantalla;

    protected CheckBox check1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);
            textData = findViewById(R.id.text4_register);
            textName = findViewById(R.id.text1_register);
            textPass= findViewById(R.id.text2_register);
            textSurname = findViewById(R.id.text3_register);
            check1= findViewById(R.id.checkbox_user);
            buttonReg= findViewById(R.id.button1_register);
            textUserName= findViewById(R.id.text6_register);
            textEmail= findViewById(R.id.text7_register);
            cajaU= findViewById(R.id.layout6_register);
            image = findViewById(R.id.image1_register);

            //Llamada a Metodo para mostrar el calendario al seleccionar la fecha
            textData.setOnClickListener(v1 -> showDatePickerDialog());
            //desactivamos el boton registrar

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
                    int yOffset = (int) (screenHeight * 0.515);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                    params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                    params.topMargin = yOffset;
                    //params.height = 80;
                    snackbarView.setLayoutParams(params);
                    snackbar.setDuration(10000);
                    snackbar.show();

                }
            });

            textPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){

                        boolean requisitosMinimos= validarContraseña(textPass.getText().toString());
                        if (!requisitosMinimos)
                        {
                            Toast.makeText(RegisterActivity.this, "La contraseña no cumple los requisitos mínimos de seguridad", Toast.LENGTH_LONG).show();
                            textPass.setText("");
                        }
                    }
                }
            });




            textUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        //cuando pierde el foco la caja de texto
                        usuarioReg();

                    }
                }
            });

            buttonReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user= new Usuarios();
                    user.UsuarioReg(textUserName.getText().toString().toLowerCase(),textName.getText().toString(),textSurname.getText().toString(),textData.getText().toString(),textEmail.getText().toString(),hashPassword(textPass.getText().toString()),check1.isChecked());
                    try {
                        bd= new GestorBBDD();
                        boolean insercionExitosa= GestorBBDDOperacionesUsuarios.insertarUsuario(user, bd);
                        if (insercionExitosa){
                            Toast.makeText(RegisterActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                            pasaPantalla= new Intent(RegisterActivity.this, LoginActivity.class);
                            //pasaPantalla = new Intent(StartActivity.this, SearchActivity.class);
                            startActivity(pasaPantalla);
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error al Crear usuario", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return insets;
        });
    }
    // Metodo para seleccionar la fecha
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year1, int monthOfYear, int dayOfMonth1) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    textData.setText(sdf.format(calendar.getTime()));
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public static String hashPassword(String pass){
        try {

            //Crear el objeto MesageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Aplicar el algoritmo de hash para la contraseña
            md.update(pass.getBytes());
            //Obtener el hash como array de bytes
            byte[] byteData= md.digest();
            //Convertir los bytes a Hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b :byteData){
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return  null;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.item_menu_register_volver:
                pasaPantalla= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(pasaPantalla);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean verificarUsuario(String username) throws  SQLException{
        boolean usuarioExiste=false;
        Connection conn= null;
        PreparedStatement st = null;
        ResultSet rs = null;


        try {
            conn=bd.conn;
            String sql=  "SELECT COUNT(*) AS count FROM usuarios WHERE nombreusuario = ?";
            st= conn.prepareStatement(sql);
            st.setString(1,username);
            rs= st.executeQuery();
            if (rs.next()){
                int count = rs.getInt("count");
                usuarioExiste = count > 0;

            }

        }finally {
            //cerrar recursos
            if (rs!=null){
                rs.close();
            }
            if(st != null){
                st.close();
            }
            if (conn!=null){
                conn.close();
            }
        }



        return usuarioExiste;
    }
    public void usuarioReg(){
        String username = textUserName.getText().toString().toLowerCase();
        if(!username.isEmpty()){
            //realizamos al consulta para comrobar si existe el usuario
            try{
                bd = new GestorBBDD();
                boolean usuarioExiste = verificarUsuario(username);
                if(usuarioExiste){
                    //Toast.makeText(RegisterActivity.this, "El nombre de usuario existe, debes cambiarlo", Toast.LENGTH_LONG).show();

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "El nombre de usuario existe, debes cambiarlo", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int screenHeight = metrics.heightPixels;
                    int yOffset = (int) (screenHeight * 0.12);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                    params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                    params.topMargin = yOffset;
                    snackbarView.setLayoutParams(params);
                    snackbar.show();

                    cajaU.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED));
                    buttonReg.setEnabled(false);

                }
                else {
                    cajaU.setDefaultHintTextColor(ColorStateList.valueOf(Color.WHITE));
                    buttonReg.setEnabled(true);
                }

            }
            catch (SQLException e){
                throw  new RuntimeException(e);

            }
        }
    }

    public static boolean validarContraseña(String contraseña) {
        // Requisitos mínimos de seguridad
        int longitudMinima = 8;
        boolean tieneNumero = false;
        boolean tieneLetraMayuscula = false;
        boolean tieneLetraMinuscula = false;
        boolean tieneCaracterEspecial = false;
        String caracteresEspeciales = "!@#$%^&*()-+";

        // Verificar longitud mínima
        if (contraseña.length() < longitudMinima) {
            return false;
        }

        // Verificar otros requisitos
        for (char c : contraseña.toCharArray()) {
            if (Character.isDigit(c)) {
                tieneNumero = true;
            } else if (Character.isUpperCase(c)) {
                tieneLetraMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneLetraMinuscula = true;
            } else if (caracteresEspeciales.contains(String.valueOf(c))) {
                tieneCaracterEspecial = true;
            }
        }

        // Comprobar que se cumplan todos los requisitos
        return tieneNumero && tieneLetraMayuscula && tieneLetraMinuscula && tieneCaracterEspecial;
    }
}