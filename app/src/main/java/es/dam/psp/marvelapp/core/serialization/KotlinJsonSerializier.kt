package es.dam.psp.marvelapp.core.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KotlinJsonSerializier <T>(private val serializier: KSerializer<T>): JsonSerializer<T> {
    override fun toJson(obj: T): String {
       return Json.encodeToString(serializier,obj)
    }

    override fun fromJson(json: String): T {
        return Json.decodeFromString(serializier,json)
    }
}