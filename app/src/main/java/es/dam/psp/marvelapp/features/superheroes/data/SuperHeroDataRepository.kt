package es.dam.psp.marvelapp.features.superheroes.data

import es.dam.psp.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHeroRepository

class SuperHeroDataRepository(
    private val localDataSource: SuperHeroXmlLocalDataSource
) : SuperHeroRepository {
    override fun getAll(): List<SuperHero> {
        return localDataSource.findAll()
    }

}
