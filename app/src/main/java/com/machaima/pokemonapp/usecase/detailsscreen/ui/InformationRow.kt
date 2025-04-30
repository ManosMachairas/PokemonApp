package com.machaima.pokemonapp.usecase.detailsscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.machaima.pokemonapp.ui.theme.Dimens

@Composable
fun InformationRow(
    title: String,
    value: String,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor,
                shape = MaterialTheme.shapes.medium
            )
            .padding(Dimens.informationRowPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(Dimens.informationRowTextPadding)
            )
            Text(
                text = value,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(Dimens.informationRowTextPadding)
            )
        }
    }
}