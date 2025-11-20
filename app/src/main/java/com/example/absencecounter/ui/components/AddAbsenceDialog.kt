package com.example.absencecounter.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absencecounter.data.model.SubjectAbsence
import kotlinx.coroutines.delay

/**
 * Modal para marcar faltas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAbsenceDialog(
    dayName: String,
    subjects: List<SubjectAbsence>,
    onDismiss: () -> Unit,
    onConfirm: (selectedIndices: List<Int>, isFullDay: Boolean) -> Unit
) {
    // state
    var isFullDay by remember { mutableStateOf(false) }
    var selectingByHour by remember { mutableStateOf(false) }
    val checked = remember { mutableStateListOf<Int>() } // índices selecionados

    var visible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.96f,
        animationSpec = spring(dampingRatio = 0.65f, stiffness = 250f),
        label = ""
    )
    LaunchedEffect(Unit) {
        delay(60)
        visible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // blur + overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(18.dp)
                .background(Color.Black.copy(alpha = 0.28f))
                .clickable { /* bloqueia clique no fundo */ }
        )

        AnimatedVisibility(visible = visible) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 20.dp)
                    .scale(scale),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 18.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Marcar falta",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                        )

                        IconButton(onClick = { onDismiss() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar"
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Opção Dia todo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = isFullDay,
                                onValueChange = { new ->
                                    isFullDay = new
                                    if (new) {
                                        selectingByHour = false
                                        checked.clear()
                                    }
                                }
                            )
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isFullDay,
                            onCheckedChange = {
                                isFullDay = it
                                if (it) {
                                    selectingByHour = false
                                    checked.clear()
                                }
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Dia todo", style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = selectingByHour,
                                onValueChange = { new ->
                                    selectingByHour = new
                                    if (new) {
                                        isFullDay = false
                                    } else {
                                        checked.clear()
                                    }
                                }
                            )
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectingByHour,
                            onCheckedChange = {
                                selectingByHour = it
                                if (it) isFullDay = false else checked.clear()
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Selecionar horário", style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(Modifier.height(12.dp))

                    AnimatedVisibility(visible = selectingByHour) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 260.dp)
                                .verticalScroll(rememberScrollState())
                                .padding(end = 4.dp)
                        ) {
                            subjects.forEachIndexed { idx, subject ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .toggleable(
                                            value = checked.contains(idx),
                                            onValueChange = { checkedNow ->
                                                if (checkedNow) checked.add(idx) else checked.remove(idx)
                                            }
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = checked.contains(idx),
                                        onCheckedChange = { checkedNow ->
                                            if (checkedNow) checked.add(idx) else checked.remove(idx)
                                        }
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = subject.name,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                onConfirm(checked.toList().sorted(), isFullDay)
                            },
                            modifier = Modifier
                                .size(52.dp)
                                .padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Confirmar",
                                tint = Color(0xFF111111)
                            )
                        }
                    }
                }
            }
        }
    }
}
