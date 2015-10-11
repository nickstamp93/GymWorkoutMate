package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

public class ExerciseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String info = getString(R.string.placeholder_ex_info, "Chest", "Shoulders , Biceps", "Compound", "Body Only");
        TextView tv = (TextView) findViewById(R.id.tvExerciseInfo);
        tv.setText(info);
    }

}
