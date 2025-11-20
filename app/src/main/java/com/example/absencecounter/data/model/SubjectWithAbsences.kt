package com.example.absencecounter.data.model

data class SubjectWithAbsences(
    val subjectId: Int,
    val name: String,
    val dayOfWeek: Int,
    val absences: Int
)
