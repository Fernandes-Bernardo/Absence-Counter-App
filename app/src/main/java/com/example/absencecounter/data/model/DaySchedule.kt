package com.example.absencecounter.data.model

/**
 * Representa a programação de um dia (ex: "Segunda-feira") com a lista de matérias.
 * dayName: o nome do dia ("Segunda-feira")
 * subjects: lista de Subject vindos do banco
 */
data class DaySchedule(
    val dayName: String,
    val subjects: List<SubjectAbsence>
)
