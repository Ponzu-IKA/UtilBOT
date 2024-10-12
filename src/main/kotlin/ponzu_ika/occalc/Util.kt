package ponzu_ika.occalc

import kotlin.math.pow
import kotlin.math.roundToInt

class Util {
    fun round(number:Double , digit: Int): Double {
        val digits = 10.0.pow(digit)
        return ((number*digits).roundToInt())/digits
    }
}