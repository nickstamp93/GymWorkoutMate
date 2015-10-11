package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.ExerciseAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Data.Database;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Database database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        initToolbar();

        init();

        setUpRecyclerView();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void init() {
        database = Database.getInstance(this);
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(this, getRecylerData());
        recyclerView.setAdapter(adapter);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private ArrayList<Exercise> getRecylerData() {
        ArrayList<Exercise> items = database.getListExercises();
        ArrayList<Integer> counts = database.getCountsByMuscle();
        items.add(0, null);
        int position = 0;
        for (int i = 0; i < EnumMuscleGroups.values().length - 1; i++) {
            position += counts.get(i) + 1;
            items.add(position, null);
        }
        return items;
    }
}
