package views

import AppState
import androidx.compose.foundation.layout.Column
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
fun HelpView(currentViewState: MutableState<AppState>) {
    Column(
        Modifier.padding(20.dp)
    ) {
        Button(
            onClick = { currentViewState.value = AppState.MENU },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Назад")
        }
        Text("""
            Добро пожаловать в игру «gameObjects.Torpedo Attack»!

            В этой игре вы управляете подлодкой, которая скрыта внизу экрана. Ваша задача - уничтожить вражеские корабли, выпуская торпеды.

            Подлодка игрока может перемещаться по полю влево и вправо, чтобы прицеливаться и атаковать врагов. Управление для первого игрока осуществляется клавишами 'A' (влево), 'D' (вправо) и 'S' (стрелять). Второй игрок использует клавиши 'J' (влево), 'L' (вправо) и 'K' (стрелять).

            Торпеда выпускается из центра нижней границы экрана игрока и движется в направлении вражеских кораблей. Пользуйтесь вашими навыками и стратегией, чтобы одержать победу!
            
            Режим одиночной игры представляет собой захватывающее испытание, где ваша цель - подбить 10 и более кораблей за отведенное время. Вам предстоит сражаться с вражеским флотом, стреляя торпедами и демонстрируя свои навыки и мастерство. Не забывайте следить за обратным отсчетом времени вверху экрана!

            В режиме двух игроков ваша задача - превзойти соперника, подбив больше кораблей, чем он. Соревнование будет напряженным, поэтому используйте свою тактику и стратегию, чтобы выйти победителем. Вашу торпеду вы видите красной, а торпеду соперника - синей.

            В верхней части экрана вы также найдете счетчик кораблей, подбитых каждым игроком, и отрезок, указывающий текущий диапазон просмотра поля в формате [startCameraX, endCameraX].
        """.trimIndent(),
            fontSize = 16.sp
        )
    }
}
