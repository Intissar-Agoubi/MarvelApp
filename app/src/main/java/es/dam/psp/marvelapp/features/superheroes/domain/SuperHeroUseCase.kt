package es.dam.psp.marvelapp.features.superheroes.domain

class SuperHeroUseCase(private  val superHeroRepository: SuperHeroRepository) {
    operator  fun invoke(): List<SuperHero>{
        return superHeroRepository.getAll()
    }

}