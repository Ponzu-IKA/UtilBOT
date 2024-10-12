package ponzu_ika.utilbot

import kotlin.math.pow
import kotlin.math.roundToInt

class Util {
    fun round(number:Double , digit: Int): Double {
        val digits = 10.0.pow(digit)
        return ((number*digits).roundToInt())/digits
    }
}