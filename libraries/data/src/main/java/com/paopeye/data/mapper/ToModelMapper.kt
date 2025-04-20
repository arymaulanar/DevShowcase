package com.paopeye.data.mapper

interface ToModelMapper<MODEL> {
    fun toModel(): MODEL
}
