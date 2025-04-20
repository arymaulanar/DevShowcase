package com.paopeye.data.mapper

interface FromEntityMapper<ENTITY, REMOTE> {
    fun fromEntity(entity: ENTITY?): REMOTE
}

