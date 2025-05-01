package com.machaima.pokemonapp.usecase.detailsscreen.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.machaima.pokemonapp.util.DETAILS_APP_BAR_BACK_ARROW

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenAppBar(
    title: String,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = DETAILS_APP_BAR_BACK_ARROW)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            navigationIconContentColor = textColor
        )
    )
}