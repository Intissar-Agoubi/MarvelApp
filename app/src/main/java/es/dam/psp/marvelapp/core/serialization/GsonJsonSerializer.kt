package es.dam.psp.marvelapp.core.serialization

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class GsonJsonSerializer<T>(private  val type : Type , private val gson: Gson = Gson()) : JsonSerializer<T> {
    override fun toJson(obj: T): String {
        return gson.toJson(obj,type)
    }

    override fun fromJson(json: String): T {
       return gson.fromJson(json,type)?: throw IllegalArgumentException("el json esta vacio o es nulo ")
    }
    companion object {
        inline fun <reified T> create(gson: Gson = Gson()): GsonJsonSerializer<T> =
            GsonJsonSerializer(object : TypeToken<T>() {}.type, gson)
    }

}