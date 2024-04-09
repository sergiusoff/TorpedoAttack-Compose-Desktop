package entities

import Player
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

abstract class GameObject(var x: Int, var y: Int, var image: ImageBitmap) {

    abstract fun move()

    @Composable
    abstract fun drawGameObject(player: Player)
}
