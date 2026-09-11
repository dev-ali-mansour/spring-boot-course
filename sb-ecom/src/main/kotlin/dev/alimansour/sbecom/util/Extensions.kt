package dev.alimansour.sbecom.util

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.roundToTwoDecimals(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}
