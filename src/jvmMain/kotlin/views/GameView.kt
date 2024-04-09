package views

import AppState
import Game
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun GameView(isTwoPlayer: Boolean = false, currentViewState: MutableState<AppState>) {
    val keysPressed = mutableSetOf<Key>()
    val listenedKeys = setOf(Key.A, Key.S, Key.D, Key.J, Key.K, Key.L, Key.Spacebar)

    MaterialTheme {
        val game = remember { Game(isTwoPlayer, currentViewState) }
        val requester = remember { FocusRequester() }

        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .onKeyEvent {
                    println(keysPressed)
                    var result = false
                    if (it.type == KeyUp && it.key in listenedKeys) {
                        keysPressed -= it.key
                        result = true
                    }
                    if (it.type == KeyDown && it.key in listenedKeys) {
                        if (game.timer.isActive || it.key == Key.Spacebar)
                            keysPressed += it.key

                        for (key in keysPressed) {
                            when (key) {
                                Key.A -> game.player1.left()
                                Key.S -> game.player1.shoot()
                                Key.D -> game.player1.right()
                                Key.J -> game.player2?.left()
                                Key.K -> game.player2?.shoot()
                                Key.L -> game.player2?.right()
                                Key.Spacebar -> {
                                    keysPressed.clear()
                                    game.clickTimer()
                                }
                            }
                        }
                        result = true
                    }
                    result
                }
                .focusRequester(requester)
                .focusable()
        ) {
            if (!isTwoPlayer)
                Spacer(modifier = Modifier.weight(1f))

            PlayerView(
                background = game.background,
                gameObjects = game.gameObjects,
                timerValue = game.timer.formattedTime,
                player = game.player1,
            )

            if (!isTwoPlayer)
                Spacer(modifier = Modifier.weight(1f))
            else {
                game.player2?.let {
                    PlayerView(
                        background = game.background,
                        gameObjects = game.gameObjects,
                        timerValue = game.timer.formattedTime,
                        player = it,
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            requester.requestFocus()
        }
    }
}
