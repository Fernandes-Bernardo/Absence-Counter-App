package com.example.absencecounter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.absencecounter.data.database.dao.AbsenceDao
import com.example.absencecounter.data.database.dao.SubjectDao
import com.example.absencecounter.data.database.entities.AbsenceEntity
import com.example.absencecounter.data.database.entities.SubjectEntity

@Database(
    entities = [
        SubjectEntity::class,
        AbsenceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun absenceDao(): AbsenceDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "absence_counter_db"
                )
                    .fallbackToDestructiveMigration() // Se mudar versão, recria db
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
