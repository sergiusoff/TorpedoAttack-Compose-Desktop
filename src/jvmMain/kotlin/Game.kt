
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import entities.EnemyShip
import entities.GameObject
import entities.Torpedo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.Rect
import utils.Timer
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Game(isTwoPlayer: Boolean, val endGame: MutableState<AppState>) {
    val background = ImageIO.read(javaClass.getResourceAsStream("background.jpg")).toComposeImageBitmap()
    var gameObjects = mutableListOf<GameObject>()
    private var gameScope = CoroutineScope(Dispatchers.Default)
    var timer = Timer(gameScope)
    private val torpedoImage = loadScaleImage("torpedo.png", .007f)
    private val opponentTorpedoImage = loadScaleImage("torpedo_opp.png", .007f)
    private val shipImage = loadScaleImage("enemy_ship.png", .2f)
    val player1: Player = Player(gameWidth = background.width, shoot = ::addTorpedo)
    val player2: Player? = if (isTwoPlayer) Player(centerX = 600, gameWidth = background.width, shoot = ::addTorpedo) else null

    init {
        addShip()
        startGameLoop()
    }

    private fun addShip() {
        val ship = EnemyShip(image = shipImage)
        gameObjects.add(ship)
    }

    private fun addTorpedo(player: Player) {
        val torpedo = Torpedo(x = player.centerX - torpedoImage.width / 2, image = torpedoImage, owner = player, opponentTorpedoImage = opponentTorpedoImage)
        gameObjects.add(torpedo)
    }

    private fun loadScaleImage(path: String, scaleCoef: Float = 1f): ImageBitmap {
        val inputStream = javaClass.getResourceAsStream("/$path")
        val originalImage = ImageIO.read(inputStream)
        val scaledImage = originalImage.getScaledInstance((originalImage.width * scaleCoef).toInt(),
            (originalImage.height * scaleCoef).toInt(), Image.SCALE_SMOOTH)
        val bufferedImage = BufferedImage((originalImage.width * scaleCoef).toInt(),
            (originalImage.height * scaleCoef).toInt(), BufferedImage.TYPE_INT_ARGB)
        bufferedImage.createGraphics().apply {
            drawImage(scaledImage, 0, 0, null)
            dispose()
        }
        return bufferedImage.toComposeImageBitmap()
    }

    private fun moveObjects() {
        val iterator = gameObjects.iterator()
        while (iterator.hasNext()) {
            val gameObject = iterator.next()
            gameObject.move()
            if (gameObject.y + gameObject.image.height < -100) {
                iterator.remove()
            }
        }
    }

    private fun checkCollisions() {
        val objectsToRemove = mutableListOf<GameObject>()

        for (i in gameObjects.indices) {
            val obj1 = gameObjects[i]
            for (j in i + 1 until gameObjects.size) {
                val obj2 = gameObjects[j]
                if (obj1 is EnemyShip && obj2 is Torpedo || obj1 is Torpedo && obj2 is EnemyShip) {
                    if (checkCollision(obj1, obj2)) {
                        objectsToRemove.add(obj1)
                        objectsToRemove.add(obj2)
                        ((if (obj1 is Torpedo) obj1 else obj2) as Torpedo).owner.points += 1
                    }
                }
            }
        }

        gameObjects.removeAll(objectsToRemove)
        if (objectsToRemove.isNotEmpty()) {
            addShip()
        }
    }

    private fun checkCollision(obj1: GameObject, obj2: GameObject): Boolean {
        val obj1Bounds = Rect(obj1.x, obj1.y, obj1.x + obj1.image.width, obj1.y + obj1.image.height)
        val obj2Bounds = Rect(obj2.x, obj2.y, obj2.x + obj2.image.width, obj2.y + obj2.image.height)
        return obj1Bounds.intersect(obj2Bounds)
    }

    private fun startGameLoop() {
        timer.start()
        gameScope.launch {
            while (timer.isActive) {
                moveObjects()
                checkCollisions()
                delay(5L)
            }
            if (timer.isEnded) {
                closeGame()
            }
        }
    }

    fun clickTimer() {
        if (timer.isActive)
            timer.pause()
        else
            startGameLoop()
    }

    private fun closeGame(){
        val result: String = if (player2 == null) {
            if (player1.points >= 10)
                "Победа!"
            else
                "Поражение!"
        } else {
            if (player1.points > player2.points)
                "Победил Игрок 1!"
            else {
                if (player1.points < player2.points)
                    "Победил Игрок 2!"
                else
                    "Ничья!"
            }
        }
        AppState.RESULT.message = result
        endGame.value = AppState.RESULT
    }
}
