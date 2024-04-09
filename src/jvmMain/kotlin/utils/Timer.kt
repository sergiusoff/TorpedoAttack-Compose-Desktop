package utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Timer(private var coroutineScope: CoroutineScope, initialTimeMillis: Long = 60000L) {

    private var timeMillis: Long = initialTimeMillis
    var isActive = false
    var isEnded = true
    private var lastTimestamp = 0L
    var formattedTime by mutableStateOf(formatTime(timeMillis))

    fun start() {
        if (isActive) return
        coroutineScope.launch {
            this@Timer.isActive = true
            isEnded = false
            lastTimestamp = System.currentTimeMillis()
            while(this@Timer.isActive) {
                delay(5L)
                timeMillis -= System.currentTimeMillis() - lastTimestamp
                if (timeMillis <= 0) {
                    timeMillis = 0
                    this@Timer.isActive = false
                    isEnded = true
                }
                lastTimestamp = System.currentTimeMillis()
                withContext(Dispatchers.Main) {
                    formattedTime = formatTime(timeMillis)
                }
            }
        }
    }

    fun pause() {
        isActive = false
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "ss:SSS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }
}
