package com.gymworkoutmate.nickstamp.gymworkoutmate.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.WorkoutsAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter.WorkoutsSelectableAdapter;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnWorkoutFragmentInteraction} interface
 * to handle interaction events.
 * Use the {@link FragmentWorkouts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWorkouts extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_WORKOUTS = "WORKOUTS";
    private static final String ARG_SELECT = "SELECT_MODE";
    private static final String ARG_CLICKABLE = "CLICKABLE";
    private static final String ARG_TARGET = "TARGET";

    private ArrayList<Workout> workouts;
    private boolean isSelectMode, clickableAdapter;
    private Class targetAcitivity;

    private OnWorkoutFragmentInteraction mListener;

    //the View for the fragment
    private View fragmentView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    /**
     * @return A new instance of fragment FragmentWorkouts.
     */
    public static FragmentWorkouts newInstance(ArrayList<Workout> list, boolean isSelectMode, boolean clickable, Class targetActivity) {
        FragmentWorkouts fragment = new FragmentWorkouts();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUTS, list);
        args.putBoolean(ARG_SELECT, isSelectMode);
        args.putBoolean(ARG_CLICKABLE, clickable);
        args.putSerializable(ARG_TARGET, targetActivity);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentWorkouts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workouts = (ArrayList<Workout>) getArguments().getSerializable(ARG_WORKOUTS);
            isSelectMode = getArguments().getBoolean(ARG_SELECT);
            clickableAdapter = getArguments().getBoolean(ARG_CLICKABLE);
            targetAcitivity = (Class) getArguments().getSerializable(ARG_TARGET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //If the view of this fragment has not been initialized yet
        // Inflate the layout for this fragment
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_fragment_workouts, container, false);

            setUpRecyclerView(fragmentView);

            refreshList();
        }

        return fragmentView;
    }

    public void refreshList() {

        if (isSelectMode) {
            ArrayList<Integer> ids = new ArrayList<>();
            for (Workout w : workouts) {
                ids.add(w.getId());
            }
            adapter = new WorkoutsSelectableAdapter(getContext(), workouts, ids);
        } else {
            adapter = new WorkoutsAdapter(getContext(), workouts, clickableAdapter, targetAcitivity);
        }
        recyclerView.setAdapter(adapter);


    }

    private void setUpRecyclerView(View parent) {
        recyclerView = (RecyclerView) parent.findViewById(R.id.rvWorkouts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnWorkoutFragmentInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWorkoutFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void changeAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnWorkoutFragmentInteraction {
        public void onFragmentInteraction(Uri uri);
    }

}
