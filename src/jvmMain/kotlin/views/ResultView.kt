package views

import AppState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultView(currentViewState: MutableState<AppState>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Button(
            onClick = { currentViewState.value = AppState.MENU },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("В меню")
        }
        Text(
            currentViewState.value.message,
            fontSize = 64.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
