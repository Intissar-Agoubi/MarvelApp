package es.dam.psp.marvelapp.features.superheroes.data

import es.dam.psp.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHeroRepository

/**
 * Clase encargada de gestionar la fuente de datos: xml, base de datos, API, etc.
 */
class SuperHeroDataRepository(private val xmlLocalDataSource: SuperHeroXmlLocalDataSource) :
    SuperHeroRepository {

    override fun getAll(): List<SuperHero> {
        return listOf(SuperHero("0", "SuperWoman", "super-woman", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz64JS016qHDB8CzBrUjltmqTGZYLkl0YRO4D1r1MyUw&s=10"))
        //return xmlLocalDataSource.findAll()
    }
}