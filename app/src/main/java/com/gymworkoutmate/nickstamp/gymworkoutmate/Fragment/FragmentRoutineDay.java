package com.gymworkoutmate.nickstamp.gymworkoutmate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRoutineDay extends Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_WORKOUTS = "ARG_WORKOUTS";


    private View placeholder;
    private View content;

    private int pos;

    private ArrayList<Workout> workouts;

    public static FragmentRoutineDay newInstance(int page, ArrayList<Workout> workouts) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putSerializable(ARG_WORKOUTS, workouts);
        FragmentRoutineDay fragment = new FragmentRoutineDay();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentRoutineDay() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt(ARG_PAGE);
        workouts = (ArrayList<Workout>) getArguments().getSerializable(ARG_WORKOUTS);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);

        placeholder = view.findViewById(R.id.placeholder);
        content = view.findViewById(R.id.workoutCard);

        if (workouts.size() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        } else {
            placeholder.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
        return view;
    }


}
