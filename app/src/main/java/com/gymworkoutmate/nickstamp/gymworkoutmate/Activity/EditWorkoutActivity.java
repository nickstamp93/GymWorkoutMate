package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.WorkoutExercisesAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Data.Database;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumExerciseTypes;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Set;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

public class EditWorkoutActivity extends AppCompatActivity {

    private Database database;
    private RecyclerView recyclerView;
    private WorkoutExercisesAdapter adapter;

    private Workout workout;
    private EditText etWorkoutName;
    private Spinner sType, sMuscle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        database = Database.getInstance(this);

        setUpToolbar();
        setUpFab();

        workout = new Workout();

        setUpInfoViews();

        setUpRecyclerView();


    }

    private void setUpInfoViews() {

        etWorkoutName = (EditText) findViewById(R.id.etWorkoutName);

        sType = (Spinner) findViewById(R.id.sWorkoutType);
        sMuscle = (Spinner) findViewById(R.id.sWorkoutMuscle);

        String[] types = new String[EnumExerciseTypes.values().length];
        int i = 0;
        for (EnumExerciseTypes type : EnumExerciseTypes.values()) {
            types[i++] = type.toString();
        }
        String[] muscles = new String[EnumMuscleGroups.values().length];
        i = 0;
        for (EnumMuscleGroups muscle : EnumMuscleGroups.values()) {
            muscles[i++] = muscle.toString();
        }

        ArrayAdapter typesAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                types);
        ArrayAdapter musclesAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                muscles);
        sType.setSelection(0);
        sType.setAdapter(typesAdapter);
        sMuscle.setSelection(0);
        sMuscle.setAdapter(musclesAdapter);

    }

    private void setUpFab() {
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
                intent.putIntegerArrayListExtra("ids", ids);

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
                ArrayList<Exercise> returnedExercises = (ArrayList<Exercise>) data.getSerializableExtra("exercises");
                ArrayList<Exercise> currentExercises = workout.getExercises();

                boolean found;

                //delete exercises that were unchecked
                for (Exercise exercise : currentExercises) {
                    //see if it was already selected
                    found = false;
                    for (Exercise e : returnedExercises) {
                        if (exercise.getId() == e.getId())
                            found = true;
                    }
                    if (!found) {
                        currentExercises.remove(exercise);
                    }
                }

                //add exercises that were checked
                //for every exercise that is returned
                for (Exercise exercise : returnedExercises) {
                    //see if it was already selected
                    found = false;
                    for (Exercise e : currentExercises) {
                        if (exercise.getId() == e.getId())
                            found = true;
                    }
                    if (!found) {
                        exercise.addSet(new Set(20));
                        exercise.addSet(new Set(15));
                        exercise.addSet(new Set(10));
                        workout.addExercise(exercise);
                    }
                }
                adapter = new WorkoutExercisesAdapter(this, workout.getExercises());
                recyclerView.setAdapter(adapter);

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_workout_save) {
            if (workout.getExercises().size() == 0) {
                Toast.makeText(EditWorkoutActivity.this, "You can't create workout without exercises!", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (etWorkoutName.getText().toString().trim().equals("")) {
                Toast.makeText(EditWorkoutActivity.this, "Enter a name for the Workout!", Toast.LENGTH_SHORT).show();
                return true;
            }
            workout.setTitle(etWorkoutName.getText().toString());
            workout.setMuscle(EnumMuscleGroups.values()[sMuscle.getSelectedItemPosition()]);
            workout.setType(EnumExerciseTypes.values()[sType.getSelectedItemPosition()]);

            database.insert(workout);
            Toast.makeText(EditWorkoutActivity.this, "Workout Created Successfully!", Toast.LENGTH_SHORT).show();
            finish();

        }

        return true;
    }
}
