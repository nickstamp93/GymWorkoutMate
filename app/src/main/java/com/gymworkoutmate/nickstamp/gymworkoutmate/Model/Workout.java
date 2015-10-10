package com.gymworkoutmate.nickstamp.gymworkoutmate.Model;

import android.database.Cursor;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumExerciseTypes;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Workout {

    private int id;
    private String title;
    private EnumExerciseTypes type;
    private EnumMuscleGroups muscle;

    public Workout(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.title = cursor.getString(1);
        this.muscle = EnumMuscleGroups.valueOf(cursor.getInt(2));
        this.type = EnumExerciseTypes.valueOf(cursor.getInt(3));
    }

    public Workout(String title, EnumExerciseTypes type, EnumMuscleGroups muscle) {
        this.title = title;
        this.type = type;
        this.muscle = muscle;
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
}
