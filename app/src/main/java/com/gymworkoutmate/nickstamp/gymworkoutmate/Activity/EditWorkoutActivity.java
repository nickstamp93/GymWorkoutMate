package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.WorkoutExercisesAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

public class EditWorkoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutExercisesAdapter adapter;

    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        setUpToolbar();

        workout = new Workout();

        setUpRecyclerView();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditWorkoutActivity.this, ExerciseListActivity.class);
                //pass the ids of the exercises that are already selected
                ArrayList<Integer> ids = new ArrayList<>();
                for (Exercise e : workout.getExercises()) {
                    ids.add(e.getId());
                }
                intent.putIntegerArrayListExtra("ids" , ids);

                startActivityForResult(intent, 100);
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvWorkoutExercises);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutExercisesAdapter(this, workout.getExercises());
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Log.i("nikos", "Called");
                workout.setExercises((ArrayList<Exercise>) data.getSerializableExtra("exercises"));
                adapter = new WorkoutExercisesAdapter(this, workout.getExercises());
                recyclerView.swapAdapter(adapter, false);

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

}
