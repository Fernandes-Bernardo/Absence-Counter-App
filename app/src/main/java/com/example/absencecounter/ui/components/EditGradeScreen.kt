package com.example.absencecounter.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absencecounter.data.model.SubjectAbsence
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditGradeScreen(
    initialSubjects: List<SubjectAbsence>,
    onBack: () -> Unit,
    onSave: (List<SubjectAbsence>) -> Unit
) {

    val subjectsState = remember {
        initialSubjects.map { it.copy() }.toMutableStateList()
    }

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    var visible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.96f,
        animationSpec = spring(dampingRatio = 0.65f, stiffness = 250f),
        label = ""
    )

    LaunchedEffect(Unit) {
        delay(80)
        visible = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(18.dp)
                .background(Color.Black.copy(alpha = 0.28f))
                .clickable {
                    focusManager.clearFocus()
                    keyboard?.hide()
                }
        )

        AnimatedVisibility(visible = visible) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 20.dp)
                    .scale(scale),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EFFF)),
                elevation = CardDefaults.cardElevation(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Editar grade",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF4A3E63))
                        )

                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                keyboard?.hide()
                                onBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar",
                                tint = Color(0xFF7E6EA8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        subjectsState.forEachIndexed { index, subject ->
                            EditableSubjectRow(
                                subject = subject,
                                onChange = { newName ->
                                    subjectsState[index] = subject.copy(name = newName)
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                keyboard?.hide()
                                onSave(subjectsState.map { it.copy() })
                                onBack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCEB7FF)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Salvar", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EditableSubjectRow(
    subject: SubjectAbsence,
    onChange: (String) -> Unit
) {
    var editing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(subject.name) }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            if (editing) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onChange(it)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            editing = false
                            focusManager.clearFocus()
                            keyboard?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFFB095FF),
                        unfocusedIndicatorColor = Color(0xFFEDE4FF),
                        cursorColor = Color(0xFF7B61FF)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { editing = true }
                        .padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (editing) {
            IconButton(
                onClick = {
                    editing = false
                    focusManager.clearFocus()
                    keyboard?.hide()
                    onChange(text)
                },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFCEB7FF))
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirmar",
                    tint = Color.White
                )
            }
        }
    }
}
