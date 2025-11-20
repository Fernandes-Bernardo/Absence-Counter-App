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
import com.example.absencecounter.ui.components.AddAbsenceDialog
import com.example.absencecounter.ui.components.ExpandableDayCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onOpenEditGrade: () -> Unit
) {
    val days by viewModel.weekSchedule.collectAsState()

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
            if (days.isEmpty()) {
                Text(
                    text = "Nenhuma grade cadastrada.",
                    modifier = Modifier.padding(32.dp),
                    fontSize = 18.sp
                )
                return@Column
            }

            days.forEachIndexed { index, schedule ->
                ExpandableDayCard(
                    schedule = schedule,
                    onEditClicked = { onOpenEditGrade() },
                    onAddClicked = {
                        addingDayIndex = index
                        showAddModal = true
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(150.dp))
        }

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
                    if (isFullDay) {
                        val ids = subjectsOfDay.map { it.id }
                        viewModel.addFullDayAbsences(ids)
                    } else {
                        val ids = selectedIndices.map { idx -> subjectsOfDay[idx].id }
                        viewModel.addSelectedAbsences(ids)
                    }

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
