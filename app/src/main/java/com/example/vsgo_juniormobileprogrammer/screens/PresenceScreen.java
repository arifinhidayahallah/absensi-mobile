package com.example.vsgo_juniormobileprogrammer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.vsgo_juniormobileprogrammer.MainActivity;
import com.example.vsgo_juniormobileprogrammer.R;
import com.example.vsgo_juniormobileprogrammer.fragments.HistoryPresencesFragment;
import com.example.vsgo_juniormobileprogrammer.fragments.PresenceFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PresenceScreen extends AppCompatActivity {

    private BottomNavigationView bottomBar;
    private MaterialToolbar topAppBarr;

    private String nim, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presence_screen);

        String[] my_credential = getIntent().getStringArrayExtra("my_credential");
        if (my_credential != null ) {
            nim = my_credential[0];
            password = my_credential[1];
        }

        topAppBarr = findViewById(R.id.topAppBar);
        topAppBarr.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileScreen.class);
            intent.putExtra("my_nim", nim);
            startActivity(intent);
        });
        topAppBarr.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.logout){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });
        PresenceFragment presenceFragment = new PresenceFragment(nim);
        HistoryPresencesFragment historyPresencesFragment = new HistoryPresencesFragment(nim);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentScreen, presenceFragment)
                .commit();
        bottomBar = findViewById(R.id.bottom_navigation);
        bottomBar.setOnItemSelectedListener(v -> {
//            switch(v.getItemId()){
//                case R.id.presensiBottomNav:
//
//                    return true;
//                case R.id.riwayatPresensiBottomNav:
//
//            }

            if(v.getItemId() == R.id.presensiBottomNav){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentScreen, presenceFragment)
                        .commit();
            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentScreen, historyPresencesFragment)
                        .commit();
            }
            return true;
        });

    }
}