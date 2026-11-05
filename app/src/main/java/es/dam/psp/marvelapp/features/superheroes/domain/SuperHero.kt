package es.dam.psp.marvelapp.features.superheroes.domain

import kotlinx.serialization.Serializable

@Serializable
data class SuperHero(val id : String , val name :String, val slug :String ,val urlImage: String)
 val superHeroLista = mutableListOf(SuperHero("1","SuperMan","super-man","Http"),
     SuperHero("2","SuperWomen","super-women","Http1234"),
     SuperHero("3","TissyMissy","tissy-missy","Http1555"))
