package com.machaima.pokemonapp.usecase.searchscreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.machaima.pokemonapp.core.domain.enumeration.PokemonType

@Composable
fun DropdownWithButton(
    expanded: Boolean,
    text: String,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelect: (String) -> Unit) {
    Box {
        Button(onClick = { onExpandedChange(true) }) {
            Text(text = text)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            PokemonType.getTypes().forEach { type ->
                DropdownMenuItem(
                    text = { Text(text = type) },
                    onClick = { onItemSelect(type) }
                )
            }
        }
    }
}