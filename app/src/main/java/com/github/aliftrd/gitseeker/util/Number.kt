package com.github.aliftrd.gitseeker.util

import kotlin.math.ln
import kotlin.math.pow

fun getFormattedNumber(count: Long): String {
    if (count < 1000) return "$count"
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    val value = count / 1000.0.pow(exp.toDouble())
    val suffix = "KMGTPE"[exp - 1]

    // Convert value to Long if it doesn't have a decimal part
    val formattedValue = if (value % 1 == 0.0) {
        String.format("%.0f", value)
    } else {
        String.format("%.1f", value)
    }

    return "$formattedValue$suffix"
}
