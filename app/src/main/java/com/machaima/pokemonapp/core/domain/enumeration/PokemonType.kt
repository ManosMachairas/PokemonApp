package com.machaima.pokemonapp.core.domain.enumeration

import androidx.compose.ui.graphics.Color
import com.machaima.pokemonapp.ui.theme.Colors

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

        fun getBackgroundColorBasedOnType(typeName: String): Color {
            return when(typeName.uppercase()) {
                FIRE.type.uppercase() -> Colors.CadmiumOrange
                WATER.type.uppercase() -> Colors.CornflowerBlue
                GRASS.type.uppercase() -> Colors.Mantis
                ELECTRIC.type.uppercase() -> Colors.Sunglow
                DRAGON.type.uppercase() -> Colors.BlueViolet
                PSYCHIC.type.uppercase() -> Colors.Strawberry
                GHOST.type.uppercase() -> Colors.DarkLavender
                DARK.type.uppercase() -> Colors.Quincy
                STEEL.type.uppercase() -> Colors.PasteBlue
                FAIRY.type.uppercase() -> Colors.MiddlePurple
                else -> Colors.Grullo
            }
        }

        fun getTextColorBasedOnType(typeName: String): Color {
            return when(typeName.uppercase()) {
                FIRE.type.uppercase() -> Colors.ChineseWhite
                WATER.type.uppercase() -> Colors.ChineseWhite
                GRASS.type.uppercase() -> Colors.OuterSpace
                ELECTRIC.type.uppercase() -> Colors.OuterSpace
                DRAGON.type.uppercase() -> Colors.ChineseWhite
                PSYCHIC.type.uppercase() -> Colors.OuterSpace
                GHOST.type.uppercase() -> Colors.ChineseWhite
                DARK.type.uppercase() -> Colors.ChineseWhite
                STEEL.type.uppercase() -> Colors.OuterSpace
                FAIRY.type.uppercase() -> Colors.OuterSpace
                else -> Colors.OuterSpace
            }
        }
    }
}