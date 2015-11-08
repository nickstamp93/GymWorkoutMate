package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.User;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;
import com.gymworkoutmate.nickstamp.gymworkoutmate.View.CircularImageView;

public class OverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREF_KEY_NAME = "pref_username";
    private static final String PREF_KEY_AGE = "pref_age";
    private static final String PREF_KEY_HEIGHT = "pref_height";
    private static final String PREF_KEY_WEIGHT = "pref_weight";
    private static final String PREF_KEY_BMI = "pref_bmi";
    private static final String PREF_KEY_FAT = "pref_fat";
    private static final String PREF_KEY_SEX = "pref_sex";

    private Toolbar toolbar;

    private SharedPreferences prefs;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setUpToolbar();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        createUser();

        createUser();

        setUpDrawer();


    }

    private void createUser() {
        String name = prefs.getString(PREF_KEY_NAME, "John Who");
        int age = prefs.getInt(PREF_KEY_AGE, 20);
        int height = prefs.getInt(PREF_KEY_HEIGHT, 180);
        double weight = prefs.getFloat(PREF_KEY_WEIGHT, (float) 80);
        double bmi = prefs.getFloat(PREF_KEY_BMI, (float) 23.5);
        double fat = prefs.getFloat(PREF_KEY_FAT, (float) 20);
        boolean male = prefs.getBoolean(PREF_KEY_SEX, true);
        currentUser = new User(name, age, height, weight, bmi, fat, male);
    }

    private void setUpDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_overview, null);
        CircularImageView profile = (CircularImageView) nav_header.findViewById(R.id.profile_photo);
        profile.setImageResource(R.drawable.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverviewActivity.this, EditProfileActivity.class));
            }
        });
        ((TextView) nav_header.findViewById(R.id.tvUsername)).setText(currentUser.getName());
        ((TextView) nav_header.findViewById(R.id.tvUserSecondaryText))
                .setText(currentUser.getHeight() + " cm , " + currentUser.getWeight() + " Kg");
        navigationView.addHeaderView(nav_header);

    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_routines) {
            startActivity(new Intent(OverviewActivity.this, RoutinesActivity.class));
        } else if (id == R.id.nav_workouts) {
            startActivity(new Intent(OverviewActivity.this, WorkoutsActivity.class));
        } else if (id == R.id.nav_exercises) {
            startActivity(new Intent(OverviewActivity.this, ExerciseListActivity.class));
        } else if (id == R.id.nav_settings) {
            Snackbar.make(toolbar, "You should enter a valid name for the workout!", Snackbar.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
