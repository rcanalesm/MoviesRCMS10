package com.ricardocanales.moviesrcm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ricardocanales.moviesrcm.dao.MovieDataBaseDao
import com.ricardocanales.moviesrcm.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {
    abstract val movieDao: MovieDataBaseDao

    companion object{
        @Volatile
        private var dbInstance : LocalDataBase? = null

        fun getInstance(context: Context): LocalDataBase{
            synchronized(this){
                var instance = dbInstance

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,LocalDataBase::class.java,"local_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    dbInstance = instance
                }
                return instance
            }
        }
    }
}