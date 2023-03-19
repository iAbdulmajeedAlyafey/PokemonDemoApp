package com.example.demoapp.domain.pokemon.usecase

import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import javax.inject.Inject

/**
 * Use cases are optional, and you should use them only when you have a business logic that is common
 * and reused by multiple view models.
 */
class GetPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    operator fun invoke(pokemonId: String) =
        pokemonRepository.getPokemonDetails(pokemonId)
}