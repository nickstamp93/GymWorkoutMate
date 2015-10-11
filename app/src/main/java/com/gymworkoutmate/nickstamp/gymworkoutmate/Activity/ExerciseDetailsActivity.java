package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private TextView tvInfo, tvName;
    private ImageView image1, image2;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        setToolbar();

        exercise = (Exercise) getIntent().getExtras().getSerializable("item");

        if (exercise == null) {
            finish();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
        }

        setUpContent();

    }

    private void setUpContent() {
        String other_muscles = "-";
        if (exercise.getOther_muscles() != null) {
            String[] tokens = exercise.getOther_muscles().split("-");
            StringBuilder s = new StringBuilder();
            for (String t : tokens) {
                s.append(EnumMuscleGroups.values()[Integer.valueOf(t) - 1].toString());
                s.append(" , ");
            }
            s.deleteCharAt(s.length() - 1);
            s.deleteCharAt(s.length() - 1);
            other_muscles = s.toString();
        }
        String mechanics = exercise.getMechanics() == 1 ? "Compound" : "Isolation";
        String muscle = exercise.getMuscle().toString();
        String equipment = exercise.getEquipment().toString();
        String info = getString(
                R.string.placeholder_ex_info,
                muscle,
                other_muscles,
                mechanics,
                equipment
        );

        tvInfo = (TextView) findViewById(R.id.tvExerciseInfo);
        tvName = (TextView) findViewById(R.id.tvExerciseTitle);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);

        image1.setImageResource(exercise.getImg1());
        image2.setImageResource(exercise.getImg2());
        tvName.setText(exercise.getTitle());
        tvInfo.setText(info);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
