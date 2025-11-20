package com.example.absencecounter.data.model

/**
 * Representa uma matéria com o número de faltas acumuladas.
 * Este é um modelo simples que será preenchido pelo banco
 */
data class SubjectAbsence(
    val id: Int,
    val name: String,
    val absences: Int
)
