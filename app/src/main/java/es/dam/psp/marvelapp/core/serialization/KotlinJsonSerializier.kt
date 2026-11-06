package es.dam.psp.marvelapp.core.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class KotlinJsonSerializier <T>(private val serializier: KSerializer<T>, private val json : Json = Json{ignoreUnknownKeys = true }): JsonSerializer<T> {
    override fun toJson(obj: T): String {
       return Json.encodeToString(serializier,obj)
    }

    override fun fromJson(json: String): T {
        return Json.decodeFromString(serializier,json)
    }
    companion object{
        inline fun <reified T> create(json: Json = Json{ ignoreUnknownKeys = true}): KotlinJsonSerializier<T> =
            KotlinJsonSerializier(serializer<T>(),json)
    }
}