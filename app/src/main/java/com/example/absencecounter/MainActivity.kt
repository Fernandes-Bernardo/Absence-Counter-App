import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Controle de faltas",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { /* TODO */ }) {
                Text("Registrar falta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* TODO */ }) {
                Text("Ver faltas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* TODO */ }) {
                Text("Configurar grade")
            }
        }
    }
}
