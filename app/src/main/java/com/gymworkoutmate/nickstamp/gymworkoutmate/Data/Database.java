package com.gymworkoutmate.nickstamp.gymworkoutmate.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Routine;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;

import java.util.ArrayList;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static Database sInstance;

    public static synchronized Database getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
            return sInstance;
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private Database(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.SQL_CREATE_TABLE_ROUTINES);
        db.execSQL(Contract.SQL_CREATE_TABLE_WORKOUTS);
        db.execSQL(Contract.SQL_CREATE_TABLE_EXERCISES);
        db.execSQL(Contract.SQL_CREATE_TABLE_EXERCISE_WORKOUTS_CONNECTION);
        db.execSQL(Contract.SQL_CREATE_TABLE_WORKOUTS_ROUTINES_CONNECTION);

        Contract.createExercises(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Routine> getListRoutines() {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.Routines.TABLE_NAME +
                        " ORDER BY " + Contract.Routines.COLUMN_TITLE, null);
        ArrayList<Routine> items = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            items.add(new Routine(c));
        }

        return items;
    }

    /**
     * @return a list with all the workouts
     */
    public ArrayList<Workout> getListWorkouts() {
        //get all workouts from the database , ordered by id (old to new)
        Cursor cWorkouts = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.Workouts.TABLE_NAME +
                        " ORDER BY " + Contract.Workouts._ID, null);
        ArrayList<Workout> items = new ArrayList<>();
        //for every workout , find the exercises that it contains
        for (cWorkouts.moveToFirst(); !cWorkouts.isAfterLast(); cWorkouts.moveToNext()) {
            //construct an arraylist with the workout's exercises
            ArrayList<Exercise> exercises = new ArrayList<>();
            Cursor cExercises = getReadableDatabase().rawQuery(
                    "SELECT * FROM " + Contract.ExerciseWorkoutConnection.TABLE_NAME +
                            " WHERE " + Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT + "='" + cWorkouts.getInt(0) + "'"
                    , null);
            for (cExercises.moveToFirst(); !cExercises.isAfterLast(); cExercises.moveToNext()) {
                Exercise e = getExercise(cExercises.getInt(1), cWorkouts.getInt(0));
                if (e != null)
                    exercises.add(e);
            }
            //create the new workout with the exercise list and add it to the workouts list to be returned
            items.add(new Workout(cWorkouts, exercises));
        }


        return items;
    }

    /**
     * @return a list with all the exercises
     */
    public ArrayList<Exercise> getListExercises() {
        //get all exercises from the database , order by muscle group
        //(that's because they are sectioned by muscle group in the list)
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.Exercises.TABLE_NAME +
                        " ORDER BY " + Contract.Exercises.COLUMN_MUSCLE + " ASC", null);
        ArrayList<Exercise> items = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            items.add(new Exercise(c));
        }
        return items;
    }

    public long insert(Routine item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Routines.COLUMN_TITLE, item.getTitle());
        contentValues.put(Contract.Routines.COLUMN_DAYS, item.getDays());
        contentValues.put(Contract.Routines.COLUMN_TYPE, item.getType().getValue());

        return getWritableDatabase().insert(Contract.Routines.TABLE_NAME, "null", contentValues);
    }


    /**
     * Insert a new workout in the database
     *
     * @param workout the workout to be inserted
     */
    public void insert(Workout workout) {
        //In order to insert a new workout , must do 2 things

        //1.Must insert the workout in the workouts table
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Workouts.COLUMN_TITLE, workout.getTitle());
        contentValues.put(Contract.Workouts.COLUMN_MUSCLE, workout.getMuscle().getValue());
        contentValues.put(Contract.Workouts.COLUMN_TYPE, workout.getType().getValue());

        long workoutId = getWritableDatabase().insert(Contract.Workouts.TABLE_NAME, "null", contentValues);

        //2.Must connect the exercises that it contains with this workout
        for (Exercise ex : workout.getExercises()) {
            insertExerciseToWorkout(ex, workoutId);
        }
    }

    /**
     * Insert an exercise in a workout
     *
     * @param exercise  the exercise object
     * @param workoutId the id of the workout
     */
    private void insertExerciseToWorkout(Exercise exercise, long workoutId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_EXERCISE, exercise.getId());
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT, workoutId);
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_NUMSETS, exercise.getSets().size());
        //Construct the sets string in the format '10-15-20'
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < exercise.getSets().size(); i++) {
            s.append(exercise.getSets().get(i).getReps());
            if (i < exercise.getSets().size() - 1)
                s.append("-");

        }
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_SETS, s.toString());
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_RESTTIME, 90);

        //insert the workout-exercise connection
        getWritableDatabase().insert(Contract.ExerciseWorkoutConnection.TABLE_NAME, "null", contentValues);
    }

    /**
     * Update an exercise that is included in a workout
     *
     * @param exercise  the exercise object
     * @param workoutId the id of the workout
     */
    private void updateExerciseOfWorkout(Exercise exercise, long workoutId) {
        //Update the connection of workout-exercise
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_EXERCISE, exercise.getId());
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT, workoutId);
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_NUMSETS, exercise.getSets().size());
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < exercise.getSets().size(); i++) {
            s.append(exercise.getSets().get(i).getReps());
            if (i < exercise.getSets().size() - 1)
                s.append("-");

        }
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_SETS, s.toString());
        contentValues.put(Contract.ExerciseWorkoutConnection.COLUMN_RESTTIME, 90);

        String[] args = {String.valueOf(workoutId), String.valueOf(exercise.getId())};
        getWritableDatabase().update(Contract.ExerciseWorkoutConnection.TABLE_NAME,
                contentValues,
                Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT + " = ? AND " + Contract.ExerciseWorkoutConnection.COLUMN_EXERCISE + " = ? ",
                args);
    }

    public void update(Routine item) {

        ContentValues values = new ContentValues();
        values.put(Contract.Routines.COLUMN_TITLE, item.getTitle());
        values.put(Contract.Routines.COLUMN_DAYS, item.getDays());
        values.put(Contract.Routines.COLUMN_TYPE, item.getType().getValue());

        getReadableDatabase().update(Contract.Routines.TABLE_NAME, values, Contract.Routines._ID + " = " + item.getId(), null);
    }

    /**
     * Update a workout
     *
     * @param workout the updated workout object
     */
    public void update(Workout workout) {

        //Update a workout item

        //Both the workouts table
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Workouts.COLUMN_TITLE, workout.getTitle());
        contentValues.put(Contract.Workouts.COLUMN_MUSCLE, workout.getMuscle().getValue());
        contentValues.put(Contract.Workouts.COLUMN_TYPE, workout.getType().getValue());

        getReadableDatabase().update(Contract.Workouts.TABLE_NAME, contentValues, Contract.Workouts._ID + " = " + workout.getId(), null);

        //and the workout-exercise connection table

        //get the exercises of the workout to be updated (from the db)
        //and create list with their ids
        ArrayList<Integer> exerciseIds = new ArrayList<>();
        Cursor cExercises = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.ExerciseWorkoutConnection.TABLE_NAME +
                        " WHERE " + Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT + "='" + workout.getId() + "'"
                , null);
        for (cExercises.moveToFirst(); !cExercises.isAfterLast(); cExercises.moveToNext()) {
            exerciseIds.add(cExercises.getInt(1));
        }
        //then , for every exercise of the updated workout , check if it is present in the
        //ids list , thus , in the database

        //exercises that are not in the updated workout , must be deleted
        boolean found;
        for (int i : exerciseIds) {
            found = false;
            for (Exercise e : workout.getExercises()) {
                if (e.getId() == i) {
                    found = true;
                }
            }
            if (!found) {
                deleteExerciseFromWorkout(i, workout.getId());
            }
        }

        //now for the updates/insertions
        for (Exercise ex : workout.getExercises()) {

            //for every exercise in the updated workout, if it was in the db , update it
            if (exerciseIds.contains(ex.getId())) {
                //update exercise
                updateExerciseOfWorkout(ex, workout.getId());

                //else insert it
            } else {
                //add exercise
                insertExerciseToWorkout(ex, workout.getId());
            }
        }
    }

    /**
     * Substract an exercise from a workout's plan
     *
     * @param exerciseId the id of the exercise
     * @param workoutId  the id of the workout
     */
    private void deleteExerciseFromWorkout(int exerciseId, int workoutId) {

        String selection = Contract.ExerciseWorkoutConnection.COLUMN_EXERCISE + " = ? AND "
                + Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT + " = ?";

        String[] selectionArgs = {String.valueOf(exerciseId), String.valueOf(workoutId)};

        getWritableDatabase().delete(Contract.ExerciseWorkoutConnection.TABLE_NAME, selection, selectionArgs);


    }

    public void deleteRoutine(int id) {

        String selection = Contract.Routines._ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        getWritableDatabase().delete(Contract.Routines.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Delete a workout
     *
     * @param id the id of the workout to be deleted
     */
    public void deleteWorkouts(int id) {

        String selection = Contract.Workouts._ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        getWritableDatabase().delete(Contract.Workouts.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Get an exercise that is contained in a particular workout plan
     *
     * @param exerciseId the id of the exercise
     * @param workoutId  the id of the workout
     * @return the Exercise object
     */
    public Exercise getExercise(int exerciseId, int workoutId) {
        //find the exercise
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.Exercises.TABLE_NAME +
                        " WHERE " + Contract.Exercises._ID + "='" + exerciseId + "'", null);

        //find the sets of the exercise in the workout
        Cursor sets = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.ExerciseWorkoutConnection.TABLE_NAME +
                        " WHERE " + Contract.ExerciseWorkoutConnection.COLUMN_EXERCISE + "='" + exerciseId + "' AND "
                        + Contract.ExerciseWorkoutConnection.COLUMN_WORKOUT + "='" + workoutId + "'", null);

        sets.moveToFirst();
        String stringSets = sets.getString(4);

        if (c.moveToFirst()) {
            return new Exercise(c, stringSets);
        } else
            return null;
    }

    /**
     * Get an array list with numbers , each number indicates the count of rows in the exercise table
     * that have the corresponding muscle group as main muscle worked . E.g. first position indicates
     * the count for the first muscle group .
     *
     * @return the list of counts
     */
    public ArrayList<Integer> getCountsByMuscle() {

        //init all counts to 0
        ArrayList<Integer> counts = new ArrayList<>();
        for (int i = 0; i < EnumMuscleGroups.values().length; i++) {
            counts.add(0);
        }
        //calculate the count of each muscle type
        for (Exercise exercise : getListExercises()) {
            switch (exercise.getMuscle().getValue()) {
                case 1:
                    counts.set(0, counts.get(0) + 1);
                    break;
                case 2:
                    counts.set(1, counts.get(1) + 1);
                    break;
                case 3:
                    counts.set(2, counts.get(2) + 1);
                    break;
                case 4:
                    counts.set(3, counts.get(3) + 1);
                    break;
                case 5:
                    counts.set(4, counts.get(4) + 1);
                    break;
                case 6:
                    counts.set(5, counts.get(5) + 1);
                    break;
                case 7:
                    counts.set(6, counts.get(6) + 1);
                    break;
            }
        }
        return counts;
    }
}
