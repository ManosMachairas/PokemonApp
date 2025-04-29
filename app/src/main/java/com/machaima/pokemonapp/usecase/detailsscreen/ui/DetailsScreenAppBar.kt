package com.machaima.pokemonapp.usecase.detailsscreen.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.machaima.pokemonapp.util.DETAILS_APP_BAR_BACK_ARROW

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenAppBar(title: String, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                Modifier
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = DETAILS_APP_BAR_BACK_ARROW)
            }
        }
    )
}