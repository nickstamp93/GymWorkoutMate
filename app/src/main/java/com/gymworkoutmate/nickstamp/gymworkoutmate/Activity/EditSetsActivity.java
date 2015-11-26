package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Set;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;
import com.squareup.picasso.Picasso;

public class EditSetsActivity extends AppCompatActivity {

    private Exercise exercise;
    private TableLayout table;
    private ImageView img1, img2;
    private EditText etReps;
    private Button bIncrease, bDecrease;
    private ImageButton bDelete;
    private int id = 1;
    private ScrollView scrollview;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.bAdd) {
                etReps.setText((Integer.valueOf(etReps.getText().toString()) + 1) + "");
            } else {
                if (Integer.valueOf(etReps.getText().toString()) > 1)
                    etReps.setText((Integer.valueOf(etReps.getText().toString()) - 1) + "");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        setUpToolbar();

        setUpFab();

        //get exercise object from intent
        exercise = (Exercise) getIntent().getExtras().getSerializable("exercise");

        setUpViews();


    }

    /**
     * init views from the xml and populate from the object passed through the intent
     */
    private void setUpViews() {

        //reps edit text
        etReps = (EditText) findViewById(R.id.etReps);

        //exercise images
        img1 = (ImageView) findViewById(R.id.image1);
        img2 = (ImageView) findViewById(R.id.image2);

        Picasso.with(this).load("file:///android_asset/Exercises/" + exercise.getImg1() + ".jpg").into(img1);
        Picasso.with(this).load("file:///android_asset/Exercises/" + exercise.getImg2() + ".jpg").into(img2);
        //buttons for increasing or decreasing reps
        bIncrease = (Button) findViewById(R.id.bAdd);
        bDecrease = (Button) findViewById(R.id.bSubstract);
        bIncrease.setOnClickListener(listener);
        bDecrease.setOnClickListener(listener);

        //delete last set button
        bDelete = (ImageButton) findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exercise.getSets().size() > 0) {
                    id--;
                    table.removeViewAt(exercise.getSets().size());
                    exercise.getSets().remove(exercise.getSets().size() - 1);
                }
            }
        });

        //table with the sets list
        table = (TableLayout) findViewById(R.id.table_sets);

        //scroll view of table layout
        scrollview = (ScrollView) findViewById(R.id.scroll_sets_table);

        //populate table layout
        for (Set s : exercise.getSets()) {
            insertViewRow(s);
        }
    }

    /**
     * Adds one more row in the table layout , according to the value of the reps edit text
     *
     * @param set the set to be added in the table
     */
    private void insertViewRow(Set set) {

        // Create a new row to be added.
        TableRow tr = new TableRow(EditSetsActivity.this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //The views inside the row
        TextView tvNum = new TextView(EditSetsActivity.this);
        TextView tvReps = new TextView(EditSetsActivity.this);

        //populate row's values
        tvNum.setText(id++ + "");
        tvNum.setGravity(Gravity.CENTER);
        tvNum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        tvReps.setText(set.getReps() + "");
        tvReps.setGravity(Gravity.CENTER);
        tvReps.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        //Add views to row
        tr.addView(tvNum);
        tr.addView(tvReps);

        //Add row to table layout
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }


    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add new set in the exercise's list
                Set set = new Set(Integer.valueOf(etReps.getText().toString()));
                exercise.addSet(set);

                //and in the UI
                insertViewRow(set);

                //scroll to the last position of the table layout
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // If calling activity is not null , this activity was started from startActivityForResult()
        //so it was called from EditWorkoutActivity , should display "Done" button

        getMenuInflater().inflate(R.menu.activity_sets, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            //Up pressed , cancel changes
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.menu_item_save_sets) {
            //if exercises are 0 , alert user
            if (exercise.getSets().size() == 0) {
                Toast.makeText(this, "You should create some sets!!!", Toast.LENGTH_SHORT).show();
                return true;
            }
            //pass the new sets list through the return intent and finish
            Intent i = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("sets", exercise.getSets());
            bundle.putInt("id", exercise.getId());
            i.putExtras(bundle);
            setResult(RESULT_OK, i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
