package com.example.weatherme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.

        val shared: AppDatabase
            get() = appDatabase ?: throw ExceptionInInitializerError("App database should be initialized. Abort.")

        @Volatile
        private var appDatabase: AppDatabase? = null
        private const val dbName = "weather-db"

        fun init(context: Context) {
            if(appDatabase != null) {
                throw ExceptionInInitializerError("App database should not be initialized twice. Abort.")
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, dbName
                ).build()
                appDatabase = instance
            }
        }

        //TODO: Remove that later.
//        fun getDatabase(context: Context): AppDatabase {
//            val tempInstance = shared
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java, "weather-db"
//                ).build()
//                shared = instance
//                return instance
//            }
//        }
    }

}