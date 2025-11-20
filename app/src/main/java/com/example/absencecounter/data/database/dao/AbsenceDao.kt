package com.example.absencecounter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.absencecounter.data.database.entities.AbsenceEntity

@Dao
interface AbsenceDao {

    @Insert
    suspend fun insertAbsence(absence: AbsenceEntity)

    @Insert
    suspend fun insertAbsences(absences: List<AbsenceEntity>)

    @Query("SELECT COUNT(*) FROM absences WHERE subjectId = :subjectId")
    suspend fun getAbsenceCountForSubject(subjectId: Int): Int

    @Query("DELETE FROM absences")
    suspend fun clearAll()
}
