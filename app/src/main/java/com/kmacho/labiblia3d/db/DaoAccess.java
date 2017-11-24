package com.kmacho.labiblia3d.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by kmachoLaptop on 11/20/2017.
 */

@Dao
public interface DaoAccess {

    @Insert
    void insertMultipleBookMarks(BookMark... bookMarks);

    @Insert
    void insertMultipleConfigurations(Configuration... configurations);

    @Insert
    void insertMultipleListBookmarks(List<BookMark> bookMarks);

    @Insert
    void insertMultipleListConfigurations(List<Configuration> configurations);

    @Insert
    void insertBookMark(BookMark bookMark);

    @Insert
    void insertConfiguration(Configuration configuration);

    @Query("SELECT * FROM Configuration")
    List<Configuration> getAllConfigurations();

    @Query("SELECT * FROM BookMark")
    List<BookMark> getAllBookmarks();

    @Query("SELECT * FROM BookMark t WHERE t.chapterId =:chapterId")
    BookMark getBookMarkById(String chapterId);

    @Query("SELECT * FROM Configuration t WHERE t.configurationId =:configurationId")
    Configuration getConfigurationById(int configurationId);

    @Update
    void updateConfiguration(Configuration configuration);

    @Update
    void updateBookMark(BookMark bookMark);

    @Delete
    void deleteConfiguration(Configuration configuration);

    @Delete
    void deleteBookMark(BookMark bookMark);
}
