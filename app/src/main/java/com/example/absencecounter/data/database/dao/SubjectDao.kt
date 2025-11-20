package com.example.absencecounter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.absencecounter.data.database.entities.SubjectEntity

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subjects WHERE dayOfWeek = :dayOfWeek ORDER BY id ASC")
    suspend fun getSubjectsByDay(dayOfWeek: Int): List<SubjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Update
    suspend fun updateSubject(subject: SubjectEntity)

    @Query("DELETE FROM subjects")
    suspend fun clearAll()
}
