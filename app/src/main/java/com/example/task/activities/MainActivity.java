package com.example.task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.task.R;
import com.example.task.fragments.HomeFragment;
import com.example.task.fragments.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_action_bar_toggle, R.string.close_action_bar_toggle);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            setTitle("Home Fragment");
        } else {
            String title = savedInstanceState.getString("title");
            setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            if (toggle.onOptionsItemSelected(item))
                return true;
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", getTitle().toString());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        selectItemDrawer(menuItem);
        return true;
    }

    private void selectItemDrawer(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new SettingFragment()).commit();
                break;
            case R.id.share:
                shareData();
                break;
            default:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    private void shareData() {

        String messageToShare = "It would be cool if you like it :) ";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageToShare);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

}