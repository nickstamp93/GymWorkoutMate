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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Set;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

public class EditSetsActivity extends AppCompatActivity {

    private Exercise exercise;
    private TableLayout table;
    private ImageView img1, img2;
    private EditText etReps;
    private Button bAdd, bSubstract;
    private int id = 1;
    private ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        setUpToolbar();

        etReps = (EditText) findViewById(R.id.etReps);

        exercise = (Exercise) getIntent().getExtras().getSerializable("exercise");

        img1 = (ImageView) findViewById(R.id.image1);
        img2 = (ImageView) findViewById(R.id.image2);
        img1.setImageResource(exercise.getImg1());
        img2.setImageResource(exercise.getImg2());

        /* Find Tablelayout defined in main.xml */
        table = (TableLayout) findViewById(R.id.table_sets);
        bAdd = (Button) findViewById(R.id.bAdd);
        bSubstract = (Button) findViewById(R.id.bSubstract);
        bAdd.setOnClickListener(listener);
        bSubstract.setOnClickListener(listener);

        scrollview = (ScrollView) findViewById(R.id.scroll_sets_table);

        for (Set s : exercise.getSets()) {
            insertViewRow(s);
        }

        setUpFab();
    }

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

    private void insertViewRow(Set set) {
        /* Create a new row to be added. */
        TableRow tr = new TableRow(EditSetsActivity.this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                /* Create a Button to be the row-content. */
        TextView tvNum = new TextView(EditSetsActivity.this);
        TextView tvReps = new TextView(EditSetsActivity.this);

        tvNum.setText(id++ + "");
        tvReps.setText(set.getReps() + "");
        tvNum.setGravity(Gravity.CENTER);
        tvReps.setGravity(Gravity.CENTER);

        tvNum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        /* Add Textviews to row. */
        tr.addView(tvNum);
        tr.addView(tvReps);
        /* Add row to TableLayout. */
        //tr.setBackgroundResource(R.drawable.sf_gradient_03);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }


    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Set set = new Set(Integer.valueOf(etReps.getText().toString()));
                exercise.addSet(set);

                insertViewRow(set);

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
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.menu_item_save_sets) {
            if (exercise.getSets().size() == 0) {
                Toast.makeText(this, "You should create some sets!!!", Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent i = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("sets", exercise.getSets());
            bundle.putInt("id", exercise.getId());
            i.putExtras(bundle);
            setResult(RESULT_OK, i);
            finish();
        } else if (id == R.id.menu_item_delete_set) {
            if (exercise.getSets().size() > 0) {
                this.id--;
                table.removeViewAt(exercise.getSets().size());
                exercise.getSets().remove(exercise.getSets().size() - 1);
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
