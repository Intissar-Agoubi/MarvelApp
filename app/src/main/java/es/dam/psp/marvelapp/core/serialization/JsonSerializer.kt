package es.dam.psp.marvelapp.core.serialization

interface JsonSerializer <T>{
    fun toJson( obj:T) : String
    fun  fromJson(json: String) : T
}