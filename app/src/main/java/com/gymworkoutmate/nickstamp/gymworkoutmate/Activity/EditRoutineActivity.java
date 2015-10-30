package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumWeekDays;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Fragment.FragmentRoutineDay;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Routine;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;
import java.util.List;

public class EditRoutineActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private boolean isCreation;

    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_routine);

        setUpToolbar();

        //if no object was passed through the intent , it's creation mode
        if (getIntent().getSerializableExtra("routine") == null) {
            routine = new Routine();
            isCreation = true;

            //else it's edit mode
        } else {
            routine = (Routine) getIntent().getSerializableExtra("routine");
            isCreation = false;
        }

        setupViewPager();

    }


    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.routineViewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < EnumWeekDays.values().length; i++) {
            adapter.addFragment(FragmentRoutineDay.newInstance(i, routine.getWorkouts()), EnumWeekDays.values()[i].toString());
        }
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //request code is 100 , for activity workout list , for selecting workouts to add
        if (requestCode == 100) {
            //if result code is ok , must take the returned list of exercises
            if (resultCode == RESULT_OK) {
                ArrayList<Workout> returnedWorkout = (ArrayList<Workout>) data.getSerializableExtra("workouts");
                int day = data.getIntExtra("day", 0);
                ArrayList<Workout> currentWorkout = routine.getWorkouts();
                //TODO current workouts of the right day

                //At first , must delete the exercise that were unchecked
                //thus , they were present in the list and they are not now
                boolean found;
                //So , if an exercise is in the current(the old) list and not in the returned list
                //delete it
                for (int i = 0; i < currentWorkout.size(); i++) {

                    found = false;
                    for (Workout w : returnedWorkout) {

                        if (currentWorkout.get(i).getId() == w.getId())
                            found = true;
                    }
                    if (!found) {
                        currentWorkout.remove(i);
                        i--;
                    }
                }

                //Then , must add the exercises that are newly checked , thus , are not in the
                //current(old) list but are in the returned list
                for (Workout workout : returnedWorkout) {

                    found = false;
                    for (Workout w : currentWorkout) {
                        if (workout.getId() == w.getId())
                            found = true;
                    }
                    if (!found) {
                        //TODO add workout to the right day of the routine
                    }
                }

                //TODO refresh fragment with the new values

            }
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
