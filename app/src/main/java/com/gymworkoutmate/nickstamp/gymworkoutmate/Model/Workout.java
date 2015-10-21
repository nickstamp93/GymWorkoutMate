package com.gymworkoutmate.nickstamp.gymworkoutmate.Model;

import android.database.Cursor;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumExerciseTypes;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Workout implements Serializable{

    private int id;
    private String title;
    private EnumExerciseTypes type;
    private EnumMuscleGroups muscle;
    private ArrayList<Exercise> exercises;

    public Workout() {
        exercises = new ArrayList<>();
    }

    public Workout(Cursor cursor, ArrayList<Exercise> exercises) {
        this.id = cursor.getInt(0);
        this.title = cursor.getString(1);
        this.muscle = EnumMuscleGroups.valueOf(cursor.getInt(2));
        this.type = EnumExerciseTypes.valueOf(cursor.getInt(3));
        this.exercises = exercises;
    }

    public Workout(String title, EnumExerciseTypes type, EnumMuscleGroups muscle, ArrayList<Exercise> exercises) {
        this.title = title;
        this.type = type;
        this.muscle = muscle;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumExerciseTypes getType() {
        return type;
    }

    public void setType(EnumExerciseTypes type) {
        this.type = type;
    }

    public EnumMuscleGroups getMuscle() {
        return muscle;
    }

    public void setMuscle(EnumMuscleGroups muscle) {
        this.muscle = muscle;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises.clear();
        this.exercises.addAll(exercises);
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }
}
