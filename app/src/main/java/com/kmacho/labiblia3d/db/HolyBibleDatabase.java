package com.kmacho.labiblia3d.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by kmachoLaptop on 11/23/2017.
 */

@Database(entities = {BookMark.class, Configuration.class}, version = 2)
public abstract class HolyBibleDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
