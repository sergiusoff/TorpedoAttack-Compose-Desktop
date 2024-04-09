package entities

import Player
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class EnemyShip(
    x: Int = Random.nextInt(-200, 1001),
    y: Int = Random.nextInt(10, 211),
    private var speed: Int = when (Random.nextInt(2)) {
        0 -> Random.nextInt(-4, -1)
        else -> Random.nextInt(1, 4)
    },
    image: ImageBitmap)
    : GameObject(x, y, image) {

    override fun move() {
        if (x + speed <= 0 && speed < 0 || x + image.width + speed >= 800 && speed > 0) {
            speed *= -1
        }
        x += speed
    }

    @Composable
    override fun drawGameObject(player: Player) {
        if (x + image.width > player.centerX - player.radius && x < player.centerX + player.radius) {
            Image(
                bitmap = image,
                contentDescription = "gameObjects.EnemyShip",
                modifier = Modifier
                    .offset(x = (x - player.centerX + player.radius).dp, y = y.dp)
                    .scale(scaleX = 1f * (if (speed > 0) 1 else -1), scaleY = 1f)
            )
        }
    }
}
