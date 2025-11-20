package com.example.absencecounter.data.repository

import com.example.absencecounter.data.database.dao.AbsenceDao
import com.example.absencecounter.data.database.dao.SubjectDao
import com.example.absencecounter.data.database.entities.AbsenceEntity
import com.example.absencecounter.data.database.entities.SubjectEntity
import com.example.absencecounter.data.model.DaySchedule
import com.example.absencecounter.data.model.SubjectAbsence

class SubjectRepository(
    private val subjectDao: SubjectDao,
    private val absenceDao: AbsenceDao
) {

    suspend fun getWeekSchedule(): List<DaySchedule> {
        val allSubjects = subjectDao.getAllSubjects()

        val grouped: Map<Int, List<SubjectEntity>> = allSubjects.groupBy { it.dayOfWeek }
        
        val result = mutableListOf<DaySchedule>()
        for (day in 1..5) {
            val subjectsOfDay = grouped[day].orEmpty()
            val uiSubjects = subjectsOfDay.map { subjectEntity ->
                val count = absenceDao.getAbsenceCountForSubject(subjectEntity.id)
                SubjectAbsence(name = subjectEntity.name, absences = count)
            }
            val dayName = when (day) {
                1 -> "Segunda-feira"
                2 -> "Terça-feira"
                3 -> "Quarta-feira"
                4 -> "Quinta-feira"
                5 -> "Sexta-feira"
                else -> "Dia $day"
            }
            result.add(DaySchedule(dayName = dayName, subjects = uiSubjects))
        }
        return result
    }

    suspend fun getSubjectIdsForDay(dayOfWeek: Int): List<Int> {
        return subjectDao.getSubjectsByDay(dayOfWeek).map { it.id }
    }

    suspend fun addAbsencesForSubjectIds(subjectIds: List<Int>) {
        if (subjectIds.isEmpty()) return
        val now = System.currentTimeMillis()
        val absences = subjectIds.map { id ->
            AbsenceEntity(subjectId = id, timestamp = now)
        }
        absenceDao.insertAbsences(absences)
    }

    suspend fun updateDaySubjects(dayOfWeek: Int, newSubjects: List<String>) {

        subjectDao.deleteSubjectsOfDay(dayOfWeek)

        val entities = newSubjects.mapIndexed { idx, name ->
            SubjectEntity(name = name, dayOfWeek = dayOfWeek, orderInDay = idx + 1)
        }
        subjectDao.insertSubjects(entities)
    }

    suspend fun insertSubject(entity: SubjectEntity) {
        subjectDao.insertSubject(entity)
    }
}
