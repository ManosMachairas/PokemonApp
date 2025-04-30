package com.machaima.pokemonapp.usecase.searchscreen.ui

import androidx.compose.foundation.border
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.ui.theme.Colors
import com.machaima.pokemonapp.ui.theme.Dimens

@Composable
fun SearchField(
    text: String,
    modifier: Modifier,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onTextChanged(it) },
        modifier = modifier.border(Dimens.searchFieldBorderWidth, Colors.CeruleanBlue),
        label = { Text(text = stringResource(id = R.string.search_pokemon_hint)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Colors.BananaYellow,
            unfocusedContainerColor = Colors.BananaYellow,
            focusedTextColor = Colors.CeruleanBlue,
            unfocusedTextColor = Colors.CeruleanBlue,
            focusedLabelColor = Colors.CeruleanBlue,
            unfocusedLabelColor = Colors.CeruleanBlue
        )

    )
}