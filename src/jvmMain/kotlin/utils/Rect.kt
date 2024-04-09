package utils

class Rect(private val left: Int, private val top: Int, private val right: Int, private val bottom: Int) {
    fun intersect(other: Rect): Boolean {
        return this.left < other.right &&
                other.left < this.right &&
                this.top < other.bottom &&
                other.top < this.bottom
    }
}