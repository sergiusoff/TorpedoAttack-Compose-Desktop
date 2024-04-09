
import kotlin.reflect.KFunction1

class Player(
    var radius: Int = 200,
    var centerX: Int = 200,
    var points: Int = 0,
    val gameWidth: Int,
    val shoot: KFunction1<Player, Unit>,
    ) {

    fun left() {
        if (centerX - radius > 0)
            centerX -= 5
    }

    fun right() {
        if (centerX + radius < gameWidth)
            centerX += 5
    }

    fun shoot() {
        shoot(this)
    }
}
