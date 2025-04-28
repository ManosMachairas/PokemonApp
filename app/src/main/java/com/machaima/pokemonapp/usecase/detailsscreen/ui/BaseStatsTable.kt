package com.machaima.pokemonapp.usecase.detailsscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.core.domain.model.pokemon.PokemonStat
import com.machaima.pokemonapp.ui.theme.Dimens

@Composable
fun BaseStatsTable(baseStats: List<PokemonStat>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(Dimens.tableColumnPadding)
    ) {
        Text(
            text = stringResource(id = R.string.base_stats_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = Dimens.baseStatsTitlePadding)
        )

        baseStats.forEachIndexed { index, stat ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.baseStatsRowPadding),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stat.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stat.value.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (index < baseStats.size - 1) {
                    Divider(modifier = Modifier.padding(vertical = Dimens.baseStatsTableDividerPaddingVertical))
                }
            }
        }
    }
}