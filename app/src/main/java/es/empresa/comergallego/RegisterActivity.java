package es.empresa.comergallego;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
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

    private  Connection bd;
    private Usuarios user;

    protected Button buttonReg;

    private Intent pasaPantalla;

    protected CheckBox check1;

    protected String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    protected String userBD = "fl0user";
    protected String password = "8Zizcvy1rMhs";


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


            textData.setOnClickListener(v1 -> showDatePickerDialog());

            buttonReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user= new Usuarios();
                    user.UsuarioReg(textUserName.getText().toString(),textName.getText().toString(),textSurname.getText().toString(),textData.getText().toString(),textEmail.getText().toString(),textPass.getText().toString(),check1.isChecked());
                    try {
                        bd  = DriverManager.getConnection(url, userBD, password);
                        GestorBBDDOperacionesUsuarios.insertarUsuario(user, bd);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return insets;
        });
    }
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

}