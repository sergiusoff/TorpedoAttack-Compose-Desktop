package views

import entities.GameObject
import Player
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlayerView(
    background: ImageBitmap,
    gameObjects: List<GameObject>,
    timerValue: String,
    player: Player,
) {
    Box(
        modifier = Modifier
            .clipToBounds()
            .width(400.dp)
            .height(600.dp)
            .horizontalScroll(rememberScrollState())
    ) {

        Image(
            bitmap = background,
            contentDescription = null,
            modifier = Modifier.graphicsLayer {
                translationX = -(player.centerX - player.radius).toFloat() // Смещаем изображение по горизонтали
            },
            contentScale = ContentScale.Crop
        )

        Row(Modifier.width(400.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = timerValue,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "[${player.centerX - player.radius}, ${player.centerX + player.radius}]",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Счёт: ${player.points}",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        gameObjects.forEach { it.drawGameObject(player) }

    }
}
