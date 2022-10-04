package com.currency.exchanger.infra.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor

object SomeUtils {
    fun convertDateTime() : String{
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss")
        val now: LocalDateTime = LocalDateTime.now()
        return dtf.format(now)
    }
    fun isInteger(d: Double): Boolean {
        // Note that Double.NaN is not equal to anything, even itself.
        return d == floor(d) && !java.lang.Double.isInfinite(d)
    }
}