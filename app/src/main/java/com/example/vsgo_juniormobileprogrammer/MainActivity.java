package com.example.vsgo_juniormobileprogrammer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vsgo_juniormobileprogrammer.database.DBHandler;
import com.example.vsgo_juniormobileprogrammer.screens.PresenceScreen;
import com.example.vsgo_juniormobileprogrammer.screens.RegisterScreen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText nimTextField, passwordTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button loginBtn = findViewById(R.id.loginBtn);
        nimTextField = findViewById(R.id.nimID);
        passwordTextField = findViewById(R.id.passwordID);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = nimTextField.getText().toString();
                String password = passwordTextField.getText().toString();
                if(nim.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Mohon lengkapi data untuk Login !", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean hasCredential = new DBHandler(MainActivity.this).getUser(nim, password);
                if(hasCredential){
                    Intent intent = new Intent(MainActivity.this, PresenceScreen.class);
                    intent.putExtra("my_credential", new String[]{nim, password});
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "NIM atau Password salah !", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    public void toRegister(View view) {
        // Handle click event here
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
//        setContentView(R.layout.register_screen);
    }

}