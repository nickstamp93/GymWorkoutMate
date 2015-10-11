package com.gymworkoutmate.nickstamp.gymworkoutmate;

import android.app.Application;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Data.Database;

/**
 * Created by nickstamp on 10/10/2015.
 */
public class GymWorkoutMateApplication extends Application {

    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Database.getInstance(getApplicationContext());
    }

    public Database getDatabase() {
        return database;
    }
}
