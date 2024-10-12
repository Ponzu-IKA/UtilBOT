package ponzu_ika.utilbot

import kotlin.math.pow
import kotlin.math.roundToLong

class Util {
    fun roundAndString(number:Double, digit: Int): String {
        val digits = 10.0.pow(digit)
        return (((number*digits).roundToLong())/digits).toBigDecimal().toPlainString()
    }
}