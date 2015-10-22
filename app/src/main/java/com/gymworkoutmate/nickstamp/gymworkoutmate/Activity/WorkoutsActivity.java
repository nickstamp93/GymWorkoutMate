package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.WorkoutsAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Data.Database;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

public class WorkoutsActivity extends AppCompatActivity {

    private Database database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        init();

        setUpToolbar();

        setUpFab();

        setUpRecyclerView();

    }

    private void init() {
        database = Database.getInstance(this);
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkoutsActivity.this, EditWorkoutActivity.class));
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvWorkouts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutsAdapter(this, database.getListWorkouts());
        recyclerView.setAdapter(adapter);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

}
