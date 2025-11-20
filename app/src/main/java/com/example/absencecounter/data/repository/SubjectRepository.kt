package com.example.absencecounter.data.repository

import com.example.absencecounter.data.database.dao.AbsenceDao
import com.example.absencecounter.data.database.dao.SubjectDao
import com.example.absencecounter.data.database.entities.AbsenceEntity
import com.example.absencecounter.data.database.entities.SubjectEntity
import com.example.absencecounter.data.model.DaySchedule
import com.example.absencecounter.data.model.SubjectAbsence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepository(
    private val subjectDao: SubjectDao,
    private val absenceDao: AbsenceDao
) {

    private val dayNames = mapOf(
        1 to "Segunda-feira",
        2 to "Terça-feira",
        3 to "Quarta-feira",
        4 to "Quinta-feira",
        5 to "Sexta-feira"
    )

    private fun Int.toDayName(): String = dayNames[this] ?: "Desconhecido"

    fun getWeekSchedule(): Flow<List<DaySchedule>> {
        return subjectDao.getAllSubjectsWithAbsences().map { list ->

            val grouped = list.groupBy { it.dayOfWeek }

            grouped.map { (dayNumber, subjectsOfDay) ->

                val subjectsMapped = subjectsOfDay.map { s ->
                    SubjectAbsence(
                        name = s.name,
                        absences = s.absences
                    )
                }

                DaySchedule(
                    dayName = dayNumber.toDayName(),
                    subjects = subjectsMapped
                )
            }
        }
    }

    suspend fun addAbsences(subjectIds: List<Int>) {
        val now = System.currentTimeMillis()

        subjectIds.forEach { id ->
            absenceDao.insertAbsence(
                AbsenceEntity(
                    subjectId = id,
                    timestamp = now
                )
            )
        }
    }

    suspend fun updateDaySubjects(
        dayName: String,
        newSubjects: List<String>
    ) {
        val dayNumber = dayNames.entries.first { it.value == dayName }.key

        subjectDao.deleteSubjectsOfDay(dayNumber)

        newSubjects.forEachIndexed { index, name ->
            subjectDao.insertSubject(
                SubjectEntity(
                    name = name,
                    dayOfWeek = dayNumber,
                    orderInDay = index
                )
            )
        }
    }
}
