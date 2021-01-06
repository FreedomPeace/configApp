package com.pccc.shoudan.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DictOne.class,DictTwo.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DictMapDao getDictDao();

    private static AppDatabase ourInstance;

    static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (ourInstance == null) {
                synchronized (AppDatabase.class) {
                    ourInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "jack.db")
                            .build();
                }
            }
        }
        return ourInstance;
    }
//    public AppDatabase() {
//    }
}
