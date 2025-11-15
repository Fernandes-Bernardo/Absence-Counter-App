package com.example.absencecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.absencecounter.ui.screens.HomeScreen
import com.example.absencecounter.ui.theme.AbsenceCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AbsenceCounterTheme {
                HomeScreen()
            }
        }
    }
}
