package com.example.absencecounter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.absencecounter.data.model.SubjectAbsence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditGradeScreen(
    initialSubjects: List<SubjectAbsence>,
    onBack: () -> Unit,
    onSave: (List<SubjectAbsence>) -> Unit
) {
    var subjects by remember {
        mutableStateOf(initialSubjects.map { it.name })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Grade") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            subjects.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        subjects = subjects.toMutableList().also { it[index] = newValue }
                    },
                    label = { Text("Matéria ${index + 1}") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    val updated = initialSubjects.mapIndexed { index, old ->
                        old.copy(name = subjects[index])
                    }
                    onSave(updated)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
