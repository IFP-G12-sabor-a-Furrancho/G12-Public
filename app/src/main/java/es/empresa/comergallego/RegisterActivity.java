package es.empresa.comergallego;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    protected EditText text4Register;
    protected  EditText text1Register;
    protected  EditText text2Register;
    protected  EditText text3Register;

    protected CheckBox check1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            text4Register = findViewById(R.id.text4_register);
            text1Register = findViewById(R.id.text1_register);
            text2Register = findViewById(R.id.text2_register);
            text3Register = findViewById(R.id.text3_register);
            check1= findViewById(R.id.checkbox_user);

            text4Register.setOnClickListener(v1 -> showDatePickerDialog());


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
                    text4Register.setText(sdf.format(calendar.getTime()));
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

}