package com.machaima.pokemonapp.usecase.searchscreen.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.machaima.pokemonapp.core.domain.enumeration.PokemonType
import com.machaima.pokemonapp.ui.theme.Colors
import com.machaima.pokemonapp.ui.theme.Dimens

@Composable
fun DropdownWithButton(
    expanded: Boolean,
    text: String,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelect: (String) -> Unit) {
    Box {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.BananaYellow,
                contentColor = Colors.CeruleanBlue
            ),
            border = BorderStroke(Dimens.buttonBorderWidth, Colors.CeruleanBlue),
            onClick = { onExpandedChange(true) }
        ) {
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