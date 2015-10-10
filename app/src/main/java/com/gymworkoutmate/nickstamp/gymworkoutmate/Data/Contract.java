package com.gymworkoutmate.nickstamp.gymworkoutmate.Data;

import android.provider.BaseColumns;

/**
 * Created by nickstamp on 10/10/2015.
 */
public class Contract {

    public static final String DATABASE_NAME = "GymWorkoutMate.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_TABLE_ROUTINES =
            "CREATE TABLE IF NOT EXISTS " + Routines.TABLE_NAME + " (" +
                    Routines._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Routines.COLUMN_TITLE + " TEXT NOT NULL," +
                    Routines.COLUMN_DAYS + " INTEGER NOT NULL," +
                    Routines.COLUMN_TYPE + " INTEGER NOT NULL" + //INTEGER BASED ON THE ENUMERATION
                    ")";

    public static final String SQL_CREATE_TABLE_WORKOUTS =
            "CREATE TABLE IF NOT EXISTS " + Workouts.TABLE_NAME + " (" +
                    Workouts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Workouts.COLUMN_TITLE + " TEXT NOT NULL," +
                    Workouts.COLUMN_MUSCLE + " INTEGER NOT NULL," + //INTEGER BASED ON THE ENUMERATION
                    Workouts.COLUMN_TYPE + " INTEGER NOT NULL" + //INTEGER BASED ON THE ENUMERATION
                    ")";

    public static final String SQL_CREATE_TABLE_EXERCISES =
            "CREATE TABLE IF NOT EXISTS " + Exercises.TABLE_NAME + " (" +
                    Exercises._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Exercises.COLUMN_TITLE + " TEXT NOT NULL," +
                    Exercises.COLUMN_IMAGE1 + " INTEGER NOT NULL," +
                    Exercises.COLUMN_IMAGE2 + " INTEGER NOT NULL," +
                    Exercises.COLUMN_MUSCLE + " INTEGER NOT NULL," + //INTEGER BASED ON THE ENUMERATION
                    Exercises.COLUMN_OTHER_MUSCLES + " TEXT," + //e.g 1-3-4 (INTEGERS BASED ON ENUMERATION)
                    Exercises.COLUMN_MECHANICS + " INTEGER NOT NULL," + //1=COMPOUND , 0=ISOLATION
                    Exercises.COLUMN_EQUIPMENT + " INTEGER NOT NULL" + //INTEGER BASED ON THE ENUMERATION
                    ")";

    public static final String SQL_CREATE_TABLE_EXERCISE_WORKOUTS_CONNECTION =
            "CREATE TABLE IF NOT EXISTS " + ExerciseWorkoutConnection.TABLE_NAME + " (" +
                    ExerciseWorkoutConnection._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ExerciseWorkoutConnection.COLUMN_EXERCISE + " INTEGER NOT NULL," +
                    ExerciseWorkoutConnection.COLUMN_WORKOUT + " INTEGER NOT NULL," +
                    ExerciseWorkoutConnection.COLUMN_NUMSETS + " INTEGER NOT NULL," +
                    ExerciseWorkoutConnection.COLUMN_SETS + " TEXT NOT NULL," +  //e.g "10-10-8-6"
                    ExerciseWorkoutConnection.COLUMN_RESTTIME + " INTEGER NOT NULL" +
                    ")";

    public static final String SQL_CREATE_TABLE_WORKOUTS_ROUTINES_CONNECTION =
            "CREATE TABLE IF NOT EXISTS " + WorkoutRoutineConnection.TABLE_NAME + " (" +
                    WorkoutRoutineConnection._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    WorkoutRoutineConnection.COLUMN_WORKOUT + " INTEGER NOT NULL," +
                    WorkoutRoutineConnection.COLUMN_ROUTINE + " INTEGER NOT NULL," +
                    WorkoutRoutineConnection.COLUMN_DAY + " INTEGER NOT NULL" + //INTEGER BASED ON THE ENUMERATION
                    ")";

    public abstract class Routines implements BaseColumns {
        public static final String TABLE_NAME = "Routines";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_TYPE = "type";
    }

    public abstract class Workouts implements BaseColumns {
        public static final String TABLE_NAME = "Workouts";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MUSCLE = "muscle";
    }

    public abstract class Exercises implements BaseColumns {
        public static final String TABLE_NAME = "Exercises";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE1 = "image1";
        public static final String COLUMN_IMAGE2 = "image2";
        public static final String COLUMN_MUSCLE = "muscle";
        public static final String COLUMN_OTHER_MUSCLES = "other_muscles";
        public static final String COLUMN_MECHANICS = "mechanics";
        public static final String COLUMN_EQUIPMENT = "equipment";
    }

    public abstract class ExerciseWorkoutConnection implements BaseColumns {
        public static final String TABLE_NAME = "ExerciseWorkoutCon";
        public static final String COLUMN_EXERCISE = "exerciseId";
        public static final String COLUMN_WORKOUT = "workoutId";
        public static final String COLUMN_NUMSETS = "numsets";
        public static final String COLUMN_SETS = "sets";
        public static final String COLUMN_RESTTIME = "rest";
    }

    public abstract class WorkoutRoutineConnection implements BaseColumns {
        public static final String TABLE_NAME = "WorkoutRoutineCon";
        public static final String COLUMN_WORKOUT = "workoutId";
        public static final String COLUMN_ROUTINE = "routineId";
        public static final String COLUMN_DAY = "day_of_week";
    }

}
