package es.dam.psp.marvelapp.features.superheroes.data.local.xml

import android.content.Context
import android.content.SharedPreferences
import es.dam.psp.marvelapp.R
import es.dam.psp.marvelapp.core.cache.AppCache
import es.dam.psp.marvelapp.core.serialization.JsonSerializer
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero

/**
 * Conector para gestionar datos en un fichero XML a través de SharedPreferences.
 *
 */


class SuperHeroXmlLocalDataSource(private val context: Context, private val serialization: JsonSerializer<List<SuperHero>>, private val appCache: AppCache) {
   private val keySuperHero = "superHero"
    //
//abrir un fichero para preparalo de ecribir y leer alli :
    val sharedPref: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.local_source_superHero),
        Context.MODE_PRIVATE
    )
   private val ttl = 5 * 60 *1000L
    //guardar toda la lista usando un solo clave
    fun save(superheroes: List<SuperHero>){
        sharedPref.edit().apply {
            putString(keySuperHero,serialization.toJson(superheroes)).apply()
        }
        appCache.updateTime(keySuperHero)

    }
    // recuperar todo o leer todo
    fun findAll(): List<SuperHero>{
        if (appCache.isExpired(keySuperHero,ttl)) return emptyList()
        val json = sharedPref.getString(keySuperHero,null)?: return  emptyList()
        return serialization.fromJson(json)
    }
    // recuperar superHero a traves su id
    fun findById(id : String) : SuperHero?{
     return findAll().find { it.id == id  }

    }
    fun deleteAll(){
        sharedPref.edit().remove(keySuperHero).apply()
    }


    }





