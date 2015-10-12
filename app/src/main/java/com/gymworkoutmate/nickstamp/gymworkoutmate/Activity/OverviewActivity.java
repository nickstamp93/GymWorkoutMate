package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Data.Database;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumExerciseTypes;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Database database = Database.getInstance(this);
//        ArrayList<Exercise> exercises = new ArrayList<>();
//        exercises.add(database.getExercise(1));
//        exercises.add(database.getExercise(2));
//        exercises.add(database.getExercise(3));
//        database.insert(new Workout("Workout 1", EnumExerciseTypes.MUSCLE_GROWTH, EnumMuscleGroups.CHEST, exercises));
//        exercises = new ArrayList<>();
//        exercises.add(database.getExercise(4));
//        exercises.add(database.getExercise(5));
//        exercises.add(database.getExercise(6));
//        database.insert(new Workout("Workout 2", EnumExerciseTypes.MUSCLE_GROWTH, EnumMuscleGroups.SHOULDERS, exercises));
//        exercises = new ArrayList<>();
//        exercises.add(database.getExercise(7));
//        exercises.add(database.getExercise(8));
//        exercises.add(database.getExercise(9));
//        exercises.add(database.getExercise(10));
//        database.insert(new Workout("Workout 3", EnumExerciseTypes.STRENGTH, EnumMuscleGroups.LEGS, exercises));

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
            Toast.makeText(OverviewActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
