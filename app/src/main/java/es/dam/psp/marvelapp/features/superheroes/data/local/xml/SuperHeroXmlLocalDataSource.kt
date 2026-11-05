package es.dam.psp.marvelapp.features.superheroes.data.local.xml

import android.content.Context
import es.dam.psp.marvelapp.R
import es.dam.psp.marvelapp.core.serialization.JsonSerializer
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero

/**
 * Conector para gestionar datos en un fichero XML a través de SharedPreferences.
 *
 */


class SuperHeroXmlLocalDataSource(private val context: Context, private val serialization: JsonSerializer<List<SuperHero>>) {
   private val keySuperHero = "superHero"
    //
//abrir un fichero para preparalo de ecribir y leer alli :
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.local_source_superHero),
        Context.MODE_PRIVATE
    )
    //guardar toda la lista usando un solo clave
    fun save(superheroes: List<SuperHero>){
        val editor = sharedPref.edit().apply {
            putString(keySuperHero,serialization.toJson(superheroes)).apply()
        }

    }
    // recuperar todo o leer todo
    fun findAll(): List<SuperHero>{
        val json =sharedPref.getString(keySuperHero,null)?: return  emptyList()
        return serialization.fromJson(json)
    }
    // recuperar superHero a traves su id
    fun findById(id : String) : SuperHero ?{
     return findAll().find { it.id == id }

    }
    fun deleteAll(id : String){
        sharedPref.edit().remove(keySuperHero).apply()
    }

    }





