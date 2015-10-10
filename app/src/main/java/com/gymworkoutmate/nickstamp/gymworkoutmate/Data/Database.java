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

    public ArrayList<Workout> getListWorkouts() {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + Contract.Workouts.TABLE_NAME +
                        " ORDER BY " + Contract.Workouts._ID, null);
        ArrayList<Workout> items = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            items.add(new Workout(c));
        }

        return items;
    }

    public ArrayList<Exercise> getListExercises() {
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

    public void insert(Workout item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Workouts.COLUMN_TITLE, item.getTitle());
        contentValues.put(Contract.Workouts.COLUMN_MUSCLE, item.getMuscle().getValue());
        contentValues.put(Contract.Workouts.COLUMN_TYPE, item.getType().getValue());

        getWritableDatabase().insert(Contract.Workouts.TABLE_NAME, "null", contentValues);
    }

    public void update(Routine item) {

        ContentValues values = new ContentValues();
        values.put(Contract.Routines.COLUMN_TITLE, item.getTitle());
        values.put(Contract.Routines.COLUMN_DAYS, item.getDays());
        values.put(Contract.Routines.COLUMN_TYPE, item.getType().getValue());

        getReadableDatabase().update(Contract.Routines.TABLE_NAME, values, Contract.Routines._ID + " = " + item.getId(), null);
    }

    public void update(Workout item) {

        ContentValues values = new ContentValues();
        values.put(Contract.Workouts.COLUMN_TITLE, item.getTitle());
        values.put(Contract.Workouts.COLUMN_TYPE, item.getType().getValue());
        values.put(Contract.Workouts.COLUMN_MUSCLE, item.getMuscle().getValue());

        getReadableDatabase().update(Contract.Workouts.TABLE_NAME, values, Contract.Workouts._ID + " = " + item.getId(), null);
    }

    public void deleteRoutine(int id) {

        // Define 'where' part of query.
        String selection = Contract.Routines._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        getWritableDatabase().delete(Contract.Routines.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteWorkouts(int id) {

        // Define 'where' part of query.
        String selection = Contract.Workouts._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        getWritableDatabase().delete(Contract.Workouts.TABLE_NAME, selection, selectionArgs);
    }


    public ArrayList<Integer> getCountsByMuscle() {
        ArrayList<Integer> counts = new ArrayList<>();
        for (int i = 0; i < EnumMuscleGroups.values().length; i++) {
            counts.add(0);
        }
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
