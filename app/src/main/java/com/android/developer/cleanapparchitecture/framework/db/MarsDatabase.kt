package com.android.developer.cleanapparchitecture.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Annotate with @Database
 * inside that annotation define entities, db version and export schema
 **/
@Database(
    entities = [MarsPropertyEntity::class],
    version = 1,
    exportSchema = false
)
/** create abstract class and extend from [RoomDatabase] **/
abstract class MarsDatabase : RoomDatabase() {

    companion object {

        private lateinit var INSTANCE: MarsDatabase

        /** Defining database name **/
        private const val DATABASE_NAME = "mars.db"

        /** Getting instance of Database **/
        fun getDatabase(context: Context): MarsDatabase {
            synchronized(MarsDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    /** Room database builder pass [Context], Database class, Database name**/
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MarsDatabase::class.java, DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }

    }

    /** Dao **/
    abstract fun marsPropertyDao(): MarsPropertyDao

}

