package views
import AppState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MenuView(currentViewState: MutableState<AppState>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { currentViewState.value = AppState.SOLO_GAME }) {
            Text("Один игрок")
        }
        Button(onClick = { currentViewState.value = AppState.DUO_GAME }) {
            Text("Два игрока")
        }
        Button(onClick = { currentViewState.value = AppState.HELP }) {
            Text("Справка")
        }
    }
}
