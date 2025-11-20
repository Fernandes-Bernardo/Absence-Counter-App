package com.example.absencecounter.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.absencecounter.data.repository.SubjectRepository
import com.example.absencecounter.data.model.DaySchedule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SubjectRepository
) : ViewModel() {

    val weekSchedule: StateFlow<List<DaySchedule>> = repository
        .getWeekSchedule()
        .map { list ->
            val order = listOf(
                "Segunda-feira",
                "Terça-feira",
                "Quarta-feira",
                "Quinta-feira",
                "Sexta-feira"
            )
            order.mapNotNull { dayName ->
                list.find { it.dayName == dayName }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addFullDayAbsences(subjectIds: List<Int>) {
        viewModelScope.launch {
            if (subjectIds.isNotEmpty()) {
                repository.addAbsences(subjectIds)
            }
        }
    }

    fun addSelectedAbsences(subjectIds: List<Int>) {
        viewModelScope.launch {
            if (subjectIds.isNotEmpty()) {
                repository.addAbsences(subjectIds)
            }
        }
    }

    fun updateDaySubjects(dayOfWeekName: String, newSubjects: List<String>) {
        viewModelScope.launch {
            repository.updateDaySubjects(dayOfWeekName, newSubjects)
        }
    }

    class Factory(private val repository: SubjectRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
