package com.example.absencecounter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.absencecounter.data.model.SubjectWithAbsences
import com.example.absencecounter.data.database.entities.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Query("DELETE FROM subjects WHERE dayOfWeek = :day")
    suspend fun deleteSubjectsOfDay(day: Int)

    @Query(
        """
        SELECT s.id AS subjectId,
               s.name AS name,
               s.dayOfWeek AS dayOfWeek,
               COUNT(a.id) AS absences
        FROM subjects s
        LEFT JOIN absences a ON a.subjectId = s.id
        GROUP BY s.id
        ORDER BY s.dayOfWeek ASC
        """
    )
    fun getAllSubjectsWithAbsences(): Flow<List<SubjectWithAbsences>>
}
