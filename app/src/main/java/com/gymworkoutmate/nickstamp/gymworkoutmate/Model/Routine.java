package com.gymworkoutmate.nickstamp.gymworkoutmate.Model;

import android.database.Cursor;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumExerciseTypes;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Routine implements Serializable {

    private String title;
    private int id, days;
    private EnumExerciseTypes type;
    private EnumMuscleGroups muscle;
    private ArrayList<Workout> workouts;

    public Routine() {

    }

    public Routine(String title, EnumExerciseTypes type, int days) {
        this.title = title;
        this.type = type;
        this.days = days;
    }

    public Routine(Cursor c, ArrayList<Workout> workouts) {
        id = c.getInt(0);
        title = c.getString(1);
        days = c.getInt(2);
        type = EnumExerciseTypes.valueOf(c.getInt(3));

        this.workouts = workouts;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumExerciseTypes getType() {
        return type;
    }

    public void setType(EnumExerciseTypes type) {
        this.type = type;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public EnumMuscleGroups getMuscle() {
        return muscle;
    }

    public void setMuscle(EnumMuscleGroups muscle) {
        this.muscle = muscle;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }
}
