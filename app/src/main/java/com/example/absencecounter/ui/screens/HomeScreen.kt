package com.example.absencecounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absencecounter.data.model.DaySchedule
import com.example.absencecounter.data.model.SubjectAbsence
import com.example.absencecounter.ui.components.AddAbsenceDialog
import com.example.absencecounter.ui.components.ExpandableDayCard

@Composable
fun HomeScreen() {

    var days by remember {
        mutableStateOf(
            listOf(
                DaySchedule(
                    dayName = "Segunda-feira",
                    subjects = listOf(
                        SubjectAbsence("Matemática", 3),
                        SubjectAbsence("História", 2),
                        SubjectAbsence("Ed. Física", 0),
                        SubjectAbsence("Projeto", 1),
                        SubjectAbsence("Física", 2)
                    )
                ),
                DaySchedule(
                    dayName = "Terça-feira",
                    subjects = listOf(
                        SubjectAbsence("Geografia", 4),
                        SubjectAbsence("Química", 2),
                        SubjectAbsence("Sociologia", 1),
                        SubjectAbsence("Biologia", 3)
                    )
                ),
                DaySchedule(
                    dayName = "Quarta-feira",
                    subjects = listOf(
                        SubjectAbsence("Química", 3),
                        SubjectAbsence("Biologia", 5),
                        SubjectAbsence("Geografia", 4),
                        SubjectAbsence("Matemática", 2)
                    )
                ),
                DaySchedule(
                    dayName = "Quinta-feira",
                    subjects = listOf(
                        SubjectAbsence("Geografia", 4),
                        SubjectAbsence("Português", 2),
                        SubjectAbsence("Sociologia", 1),
                        SubjectAbsence("Química", 2)
                    )
                ),
                DaySchedule(
                    dayName = "Sexta-feira",
                    subjects = listOf(
                        SubjectAbsence("Projeto", 0),
                        SubjectAbsence("História", 1),
                        SubjectAbsence("Filosofia", 0),
                        SubjectAbsence("Ed. Física", 0)
                    )
                )
            )
        )
    }

    // ===========================
    //   MODAL DE EDITAR GRADE
    // ===========================
    var showEditModal by remember { mutableStateOf(false) }
    var editingDayIndex by remember { mutableStateOf<Int?>(null) }

    // ===========================
    //   MODAL DE ADICIONAR FALTA
    // ===========================
    var showAddModal by remember { mutableStateOf(false) }
    var addingDayIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        bottomBar = { Footer() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            days.forEachIndexed { index, schedule ->

                ExpandableDayCard(
                    schedule = schedule,

                    onEditClicked = {
                        editingDayIndex = index
                        showEditModal = true
                    },

                    onAddClicked = {
                        addingDayIndex = index
                        showAddModal = true
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(150.dp))
        }

        // =====================================
        //  MODAL DE EDITAR GRADE (JÁ EXISTENTE)
        // =====================================
        if (showEditModal && editingDayIndex != null) {
            EditGradeScreen(
                initialSubjects = days[editingDayIndex!!].subjects,

                onBack = {
                    showEditModal = false
                    editingDayIndex = null
                },

                onSave = { updatedSubjects ->
                    val idx = editingDayIndex!!
                    val newList = days.toMutableList()
                    newList[idx] = days[idx].copy(subjects = updatedSubjects)
                    days = newList.toList()

                    showEditModal = false
                    editingDayIndex = null
                }
            )
        }

        // =========================================
        //   MODAL DE ADICIONAR FALTA — NOVO 🎉
        // =========================================
        if (showAddModal && addingDayIndex != null) {

            val dayIndex = addingDayIndex!!
            val subjectsOfDay = days[dayIndex].subjects

            AddAbsenceDialog(
                dayName = days[dayIndex].dayName,
                subjects = subjectsOfDay,

                onDismiss = {
                    showAddModal = false
                    addingDayIndex = null
                },

                onConfirm = { selectedIndices, isFullDay ->

                    val newDays = days.toMutableList()

                    val updatedSubjects =
                        if (isFullDay) {
                            // Dia todo → soma 1 falta em todas as matérias
                            subjectsOfDay.map { subject ->
                                subject.copy(absences = subject.absences + 1)
                            }
                        } else {
                            // Seleção de horários individuais
                            subjectsOfDay.mapIndexed { idx, subject ->
                                if (selectedIndices.contains(idx))
                                    subject.copy(absences = subject.absences + 1)
                                else subject
                            }
                        }

                    newDays[dayIndex] = days[dayIndex].copy(subjects = updatedSubjects)
                    days = newDays.toList()

                    showAddModal = false
                    addingDayIndex = null
                }
            )
        }
    }
}

@Composable
fun Footer() {

    val purpleBackground = Color(0xFF9C84FF)
    val ellipseColor = Color(0xFFF4EFFF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(purpleBackground)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .shadow(
                    elevation = 18.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .clip(CircleShape)
                .background(ellipseColor)
                .padding(horizontal = 44.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Controlador de faltas",
                fontSize = 20.sp,
                color = Color(0xFF4A3D66)
            )
        }
    }
}
