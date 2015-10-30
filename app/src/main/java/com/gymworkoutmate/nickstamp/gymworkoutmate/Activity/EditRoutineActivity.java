package com.gymworkoutmate.nickstamp.gymworkoutmate.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumWeekDays;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Fragment.FragmentRoutineDay;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Routine;
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
//            fillUIFromWorkout();
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
