package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private Workout workout = null;
    private EditText etWorkoutName;
    private Spinner sType, sMuscle;

    private boolean isCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        database = Database.getInstance(this);

        setUpToolbar();

        setUpFab();

        setUpInfoViews();

        setUpRecyclerView();

        if (getIntent().getSerializableExtra("workout") == null) {
            workout = new Workout();
            isCreation = true;
        } else {
            workout = (Workout) getIntent().getSerializableExtra("workout");
            fillUIFromWorkout();
            refreshExerciseList(workout.getExercises());
            isCreation = false;
        }

    }

    private void fillUIFromWorkout() {
        etWorkoutName.setText(workout.getTitle());
        sType.setSelection(workout.getType().getValue() - 1);
        sMuscle.setSelection(workout.getMuscle().getValue() - 1);
    }

    /**
     * Init and Set up the views about the info of the workout such as the name , muscle and type
     */
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
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                int id = data.getIntExtra("id", 0);
                for (Exercise e : workout.getExercises()) {
                    if (e.getId() == id) {
                        e.setSets(((ArrayList<Set>) data.getExtras().getSerializable("sets")));
                    }
                }
                refreshExerciseList(workout.getExercises());
            }
        }
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                ArrayList<Exercise> returnedExercises = (ArrayList<Exercise>) data.getSerializableExtra("exercises");
                ArrayList<Exercise> currentExercises = workout.getExercises();

                boolean found;

                //delete exercises that were unchecked
                for (int i = 0; i < currentExercises.size(); i++) {

                    //see if it was already selected
                    found = false;
                    for (Exercise e : returnedExercises) {

                        if (currentExercises.get(i).getId() == e.getId())
                            found = true;
                    }
                    if (!found) {
                        currentExercises.remove(i);
                        i--;
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
                        exercise.addSet(new Set(10));
                        exercise.addSet(new Set(10));
                        exercise.addSet(new Set(10));
                        workout.addExercise(exercise);
                    }
                }

                refreshExerciseList(workout.getExercises());

            }
        }
    }

    private void refreshExerciseList(ArrayList<Exercise> newList) {
        adapter = new WorkoutExercisesAdapter(this, newList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCreation)
            getMenuInflater().inflate(R.menu.activity_add_workout, menu);
        else
            getMenuInflater().inflate(R.menu.activity_edit_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        if (id == R.id.action_workout_delete) {
            database.deleteWorkouts(workout.getId());
            Toast.makeText(EditWorkoutActivity.this, workout.getTitle() + " deleted!", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (id == R.id.action_workout_save) {

            //if there are no exercises selected , alert
            if (workout.getExercises().size() == 0) {
                Toast.makeText(EditWorkoutActivity.this, "Your workout should have at least one exercise!", Toast.LENGTH_SHORT).show();
                return true;
            }
            //if name has not been set , alert
            if (etWorkoutName.getText().toString().trim().equals("")) {
                Toast.makeText(EditWorkoutActivity.this, "Enter a valid name for your Workout!", Toast.LENGTH_SHORT).show();
                return true;
            }
            //assign values to the workout
            workout.setTitle(etWorkoutName.getText().toString());
            workout.setMuscle(EnumMuscleGroups.values()[sMuscle.getSelectedItemPosition()]);
            workout.setType(EnumExerciseTypes.values()[sType.getSelectedItemPosition()]);

            //if it's creation mode , add the new workout in the db
            if (isCreation) {

                database.insert(workout);
                Toast.makeText(EditWorkoutActivity.this, "Workout Created Successfully!", Toast.LENGTH_SHORT).show();

                //else it's update mode , so update the workout
            } else {
                database.update(workout);
                Toast.makeText(EditWorkoutActivity.this, "Workout Updated Successfully!", Toast.LENGTH_SHORT).show();
            }

            finish();

        }

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }
}
