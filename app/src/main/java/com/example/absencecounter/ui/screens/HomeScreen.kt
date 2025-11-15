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
import com.example.absencecounter.ui.components.ExpandableDayCard

@Composable
fun HomeScreen() {

    val mockData = remember {
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
    }

    Scaffold(
        bottomBar = {
            FooterFigmaStyled()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            mockData.forEach { schedule ->
                ExpandableDayCard(schedule = schedule)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Composable
fun FooterFigmaStyled() {

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
