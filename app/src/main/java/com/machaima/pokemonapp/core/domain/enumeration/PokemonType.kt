package com.machaima.pokemonapp.core.domain.enumeration

enum class PokemonType(val type: String) {
    ALL("All"),
    FIRE("Fire"),
    WATER("Water"),
    GRASS("Grass"),
    ELECTRIC("Electric"),
    DRAGON("Dragon"),
    PSYCHIC("Psychic"),
    GHOST("Ghost"),
    DARK("Dark"),
    STEEL("Steel"),
    FAIRY("Fairy");

    companion object {
        fun getTypes(): List<String> =
            entries.map { it.type }
    }
}