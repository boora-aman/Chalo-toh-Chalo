package com.example.chalotohchalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    FrameLayout container;
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bnView = findViewById(R.id.bnView);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.nav_home){
                    loadFrag(new home_fragment(), false);
                }
                else if(id==R.id.nav_my_booking){
                    loadFrag(new MyBooking_fragment(), false);
                }
                else{
                    loadFrag(new MyProfile_fragment(), false);
                }

                return true;
            }
        });

        bnView.setSelectedItemId(R.id.nav_home);
    }

    public void loadFrag(Fragment fragment,boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag){
            ft.add(R.id.container, fragment);
        }
        else{
            ft.replace(R.id.container, fragment);
        }
        ft.commit();
    }
}