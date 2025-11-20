package com.example.absencecounter.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "absences")
data class AbsenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val subjectId: Int,
    val timestamp: Long
)
