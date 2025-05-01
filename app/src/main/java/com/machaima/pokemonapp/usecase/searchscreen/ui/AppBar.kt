package com.machaima.pokemonapp.usecase.searchscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.ui.theme.Dimens.appBarImageHeight
import com.machaima.pokemonapp.ui.theme.Dimens.appBarImageWidth
import com.machaima.pokemonapp.util.APPBAR_CENTER_IMAGE_DESCRIPTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokemon_logo),
                    contentDescription = APPBAR_CENTER_IMAGE_DESCRIPTION,
                    modifier = Modifier.size(
                        height = appBarImageHeight,
                        width = appBarImageWidth
                    )
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}