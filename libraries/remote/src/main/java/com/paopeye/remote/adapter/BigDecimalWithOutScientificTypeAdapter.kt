package com.paopeye.remote.adapter

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.paopeye.kit.extension.orEmpty
import java.lang.reflect.Type
import java.math.BigDecimal

internal class BigDecimalWithOutScientificTypeAdapter : JsonSerializer<BigDecimal> {
    override fun serialize(
        src: BigDecimal?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(disableScientificNotation(src.orEmpty()).toBigDecimal())
    }

    private fun disableScientificNotation(src: BigDecimal) = src.toPlainString()
}
