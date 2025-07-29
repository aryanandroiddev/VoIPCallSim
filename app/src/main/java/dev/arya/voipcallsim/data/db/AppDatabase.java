package dev.arya.voipcallsim.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.arya.voipcallsim.data.model.CallLog;

@Database(entities = {CallLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase instance;
    public abstract CallLogDao callLogDao();
    public static final String DATABASE_NAME = "voip_call_db";

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
