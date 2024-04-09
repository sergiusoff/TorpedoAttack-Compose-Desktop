import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import views.GameView
import views.HelpView
import views.MenuView
import views.ResultView

@Preview
@Composable
fun App() {
    val currentViewState = remember { mutableStateOf(AppState.MENU) }

    when (currentViewState.value) {
        AppState.MENU -> MenuView(currentViewState)
        AppState.SOLO_GAME -> GameView(isTwoPlayer = false, currentViewState)
        AppState.DUO_GAME -> GameView(isTwoPlayer = true, currentViewState)
        AppState.HELP -> HelpView(currentViewState)
        AppState.RESULT -> ResultView(currentViewState)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
