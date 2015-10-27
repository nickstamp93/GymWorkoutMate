package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.os.Bundle;
import android.app.Activity;

import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

public class EditRoutineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
