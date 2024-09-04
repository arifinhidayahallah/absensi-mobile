package com.example.vsgo_juniormobileprogrammer.screens;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.vsgo_juniormobileprogrammer.R;
import com.example.vsgo_juniormobileprogrammer.database.DBHandler;
import com.example.vsgo_juniormobileprogrammer.models.UserModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterScreen extends AppCompatActivity {
    private TextInputEditText nimTextField, fullnameTextField, birthdateTextField,passwordTextField, passwordConfirmationTextField;
    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        FragmentManager fragment = getSupportFragmentManager();
        db = new DBHandler(this);
        TextView toLogin = findViewById(R.id.toLogin);

        String[] genders = {"Laki-laki", "Perempuan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_gender, genders);
        AutoCompleteTextView genderACTV = findViewById(R.id.genderACTV);
        genderACTV.setInputType(InputType.TYPE_NULL);
        genderACTV.setAdapter(adapter);
        Button datePickerBtn = findViewById(R.id.datePickerBtn);
        datePickerBtn.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pilih Tanggal Lahir")
                    .setTheme(R.style.DatePickerStyle)
                    .build();
            datePicker.show(fragment, "Date Picker");
            datePicker.addOnPositiveButtonClickListener((l)->{
                System.out.println("Date Choosed");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(l);
                Date date = new Date();
                date.setTime(l);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateFormatted = simpleDateFormat.format(date);
                birthdateTextField.setText(dateFormatted);
            });

        });

        Button registerBtn = findViewById(R.id.registerBtn);
        fullnameTextField = findViewById(R.id.fullnameID);
        nimTextField = findViewById(R.id.nimID);
        passwordTextField = findViewById(R.id.passwordID);
        birthdateTextField = findViewById(R.id.birthdateID);
        birthdateTextField.setInputType(InputType.TYPE_NULL);

        toLogin.setOnClickListener(v -> {
            finish();
        });
        registerBtn.setOnClickListener(v -> {
            try{
                String nim = nimTextField.getText().toString();
                String fullname = fullnameTextField.getText().toString();
                String birthdate = birthdateTextField.getText().toString();
                String password = passwordTextField.getText().toString();
                String gender = genderACTV.getText().toString();
                if(nim.isEmpty() || fullname.isEmpty() || birthdate.isEmpty() || password.isEmpty()){
                    Toast errorToast = Toast.makeText(this, "Data tidak boleh ko" +
                            "song !", Toast.LENGTH_LONG);
                    errorToast.show();
                    return;
                }
                UserModel userModel = new UserModel(nim, fullname, birthdate, gender, password);
                Boolean isRegisterSuccess = db.registerUser(userModel);
                if(isRegisterSuccess){
                    Toast.makeText(this, "Registrasi Akun Berhasil !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "NIM sudah digunakan !", Toast.LENGTH_LONG).show();
                }

                nimTextField.setText("");
                fullnameTextField.setText("");
                birthdateTextField.setText("");
                passwordTextField.setText("");
            }catch(Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
//            setContentView(R.layout.activity_main);
//            System.out.println(fullnameTextField.getText());
//            System.out.println(nimTextField.getEditText().getText());
//            System.out.println(passwordTextField.getEditText().getText());
//            System.out.println(passwordConfirmationTextField.getEditText().getText());
        });

    }

    public void backToLogin(View view){

//        setContentView(R.layout.activity_main);
//        System.out.println("Haloo");
//        finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }
}