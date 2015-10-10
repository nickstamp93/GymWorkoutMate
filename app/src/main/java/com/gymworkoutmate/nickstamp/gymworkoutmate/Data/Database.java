package com.gymworkoutmate.nickstamp.gymworkoutmate.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


}
