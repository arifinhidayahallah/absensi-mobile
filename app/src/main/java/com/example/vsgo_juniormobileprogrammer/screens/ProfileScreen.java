package com.example.vsgo_juniormobileprogrammer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.vsgo_juniormobileprogrammer.R;
import com.example.vsgo_juniormobileprogrammer.database.DBHandler;
import com.example.vsgo_juniormobileprogrammer.models.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileScreen extends AppCompatActivity {

    private TextInputEditText nimInputText, fullnameInputText, birthdateInputText, passwordInputText;
    private MaterialAutoCompleteTextView genderACTV;

    private Button datePickerBtn, changeProfileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        String nim = getIntent().getStringExtra("my_nim").toString();
        UserModel userModel = new DBHandler(this).getUser(nim);

        nimInputText = findViewById(R.id.nimIDProfile);
        fullnameInputText = findViewById(R.id.fullnameIDProfile);
        birthdateInputText = findViewById(R.id.birthdateIDProfile);
        passwordInputText = findViewById(R.id.passwordIDProfile);

        String[] genders = {"Laki-laki", "Perempuan"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.list_gender, genders);
        genderACTV = findViewById(R.id.genderACTVProfile);
        genderACTV.setInputType(InputType.TYPE_NULL);
        genderACTV.setText(userModel.getGender());
        genderACTV.setAdapter(adapter);

        nimInputText.setInputType(InputType.TYPE_NULL);
        birthdateInputText.setInputType(InputType.TYPE_NULL);
//        fullnameInputText.setEnabled(false);
//        genderInputText.setEnabled(false);
//        passwordInputText.setEnabled(false);

        nimInputText.setText(userModel.getNim());
        fullnameInputText.setText(userModel.getFullname());
        birthdateInputText.setText(userModel.getBirthdate());
        passwordInputText.setText(userModel.getPassword());
        datePickerBtn = findViewById(R.id.datePickerBtnProfile);

        datePickerBtn.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Pilih Tanggal Lahir")
                            .setTheme(R.style.DatePickerStyle)
                            .build();
            materialDatePicker.show(getSupportFragmentManager(), "datePicker");
            materialDatePicker.addOnPositiveButtonClickListener(l->{
                System.out.println("Date Choosed");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(l);
                Date date = new Date();
                date.setTime(l);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateFormatted = simpleDateFormat.format(date);
                birthdateInputText.setText(dateFormatted);
            });
        });

        changeProfileBtn = findViewById(R.id.changeProfileButton);
        changeProfileBtn.setOnClickListener(v -> {
            String fullname = fullnameInputText.getText().toString();
            String birthdate = birthdateInputText.getText().toString();
            String gender = genderACTV.getText().toString();
            String password = passwordInputText.getText().toString();
            userModel.setFullname(fullname);
            userModel.setBirthdate(birthdate);
            userModel.setGender(gender);
            userModel.setPassword(password);
            Boolean isUpdateSuccess = new DBHandler(this).changeUserProfile(userModel);
            if(isUpdateSuccess){
                Toast.makeText(getApplicationContext(), "Perubahan berhasil !", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Perubahan gagal !", Toast.LENGTH_LONG).show();
            }
        });
    }

}