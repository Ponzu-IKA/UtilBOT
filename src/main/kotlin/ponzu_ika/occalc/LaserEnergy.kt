package ponzu_ika.occalc

import kotlin.math.ln
import kotlin.math.log
import kotlin.math.pow

class LaserEnergy {
    private fun everyLasers(number: Int, base:Double, max:Double): Double {
        if(number < 1 || max < 0 || base < 0) return 1.0
        val r = 0.1
        val cI = base.pow((1+r)* log((1+r)/r,max))
        val energy = max.pow((ln((1+r)/ (cI.pow(-number)+r) ) / ln((1+r)/r))) * ((1+r*number*cI.pow(number))/(1+r*cI.pow(number)))

        return energy
    }

    fun calc(blueCount: Int = 0,greenCount: Int = 0,redCount: Int = 0): Double {
        val blueEnergy = everyLasers(blueCount, 2.5, 1000.0)
        val greenEnergy = everyLasers(greenCount, 1.8, 300.0)
        val redEnergy = everyLasers(redCount, 1.5, 100.0)
        return blueEnergy*greenEnergy*redEnergy-1
    }
}