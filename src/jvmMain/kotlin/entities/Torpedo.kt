package entities

import Player
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

class Torpedo(x: Int,
              y: Int = 600,
              image: ImageBitmap,
              val owner: Player,
              private var speed: Int = -6,
              private val opponentTorpedoImage: ImageBitmap)
    : GameObject(x, y, image) {

    override fun move() {
        y += speed
    }

    @Composable
    override fun drawGameObject(player: Player) {
        if (x + image.width > player.centerX - player.radius && x < player.centerX + player.radius) {
            Image(
                bitmap = if (player == owner) image else opponentTorpedoImage,
                contentDescription = "gameObjects.Torpedo",
                modifier = Modifier.offset(x = (x - player.centerX + player.radius).dp, y = y.dp)
            )
        }
    }
}
