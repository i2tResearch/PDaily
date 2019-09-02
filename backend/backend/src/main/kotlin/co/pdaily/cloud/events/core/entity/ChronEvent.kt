package co.pdaily.cloud.events.core.entity

import java.time.Instant

interface ChronEvent {

    val type: EventType
    val occurencyDate: Instant


    fun isType(type: EventType): Boolean{
        return this.type == type
    }
}
