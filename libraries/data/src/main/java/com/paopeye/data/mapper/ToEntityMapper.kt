package com.paopeye.data.mapper

interface ToEntityMapper<ENTITY> {
    fun toEntity(): ENTITY
}
