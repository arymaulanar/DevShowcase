package com.paopeye.kit.extension

import com.paopeye.kit.constant.EmptyValues
import java.math.BigDecimal

fun emptyString(): String = EmptyValues.STRING

fun Int?.orEmpty(): Int = this ?: EmptyValues.INT

fun emptyInt(): Int = EmptyValues.INT

fun Double?.orEmpty(): Double = this ?: EmptyValues.DOUBLE

fun emptyDouble(): Double = EmptyValues.DOUBLE

fun Float?.orEmpty(): Float = this ?: EmptyValues.FLOAT

fun emptyFloat(): Float = EmptyValues.FLOAT

fun Long?.orEmpty(): Long = this ?: EmptyValues.LONG

fun emptyLong(): Long = EmptyValues.LONG

fun Char?.orEmpty(): Char = this ?: EmptyValues.CHAR

fun emptyChar(): Char = EmptyValues.CHAR

fun Int?.orEmptyIndex(): Int = this ?: EmptyValues.INDEX

fun emptyIndex(): Int = EmptyValues.INDEX

fun ByteArray?.orEmpty(): ByteArray = this ?: EmptyValues.BYTE_ARRAY

fun emptyByteArray(): ByteArray = EmptyValues.BYTE_ARRAY

fun BigDecimal?.orEmpty(): BigDecimal = this ?: BigDecimal.ZERO

fun emptyBigDecimal(): BigDecimal = BigDecimal.ZERO

fun <T> List<T>?.orEmpty(): List<T> = this ?: emptyList()

fun Boolean?.orFalse(): Boolean = this ?: false
