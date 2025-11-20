package com.example.absencecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.absencecounter.data.database.AppDatabase
import com.example.absencecounter.data.repository.SubjectRepository
import com.example.absencecounter.navigation.NavGraph
import com.example.absencecounter.ui.screens.HomeViewModel
import com.example.absencecounter.ui.theme.AbsenceCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val db = remember { AppDatabase.getDatabase(context) }
            val repository = remember { SubjectRepository(db.subjectDao(), db.absenceDao()) }
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(repository))
            val navController = rememberNavController()

            AbsenceCounterTheme {
                NavGraph(navController = navController, homeViewModel = homeViewModel)
            }
        }
    }
}
