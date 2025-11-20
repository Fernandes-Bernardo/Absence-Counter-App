package com.example.absencecounter.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absencecounter.data.model.DaySchedule
import com.example.absencecounter.data.model.SubjectAbsence

/**
 * Card expansível que mostra o dia e,
 * quando expandido, lista as matérias com suas faltas.
 */
@Composable
fun ExpandableDayCard(
    schedule: DaySchedule,
    onAddClicked: (String) -> Unit = {},
    onEditClicked: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp)
            .animateContentSize()
    ) {

        // Header
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFE6E5E5),
            tonalElevation = if (expanded) 8.dp else 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 78.dp)
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = schedule.dayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp
                )

                Icon(
                    imageVector =
                        if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Fechar" else "Abrir"
                )
            }
        }

        if (expanded) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color(0xFFE6E5E5),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    schedule.subjects.forEach { subject: SubjectAbsence ->
                        Text(
                            text = "${subject.name} - ${subject.absences} faltas",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = { onEditClicked(schedule.dayName) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Editar grade"
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = { onAddClicked(schedule.dayName) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Adicionar falta"
                            )
                        }
                    }
                }
            }
        }
    }
}
