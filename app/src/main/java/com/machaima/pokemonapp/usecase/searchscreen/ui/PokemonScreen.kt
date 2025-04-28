package com.machaima.pokemonapp.usecase.searchscreen.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.navigation.ScreenEndpoints
import com.machaima.pokemonapp.ui.theme.Dimens
import com.machaima.pokemonapp.usecase.searchscreen.viewmodel.SearchScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun PokemonScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val pokemonList = viewModel.pokemonList
    val isLoading = viewModel.isLoading
    val showDialog = viewModel.showDialog
    val hasMoreResultsToLoad = viewModel.hasMoreResultsToLoad
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//    val previousHasMoreResults = remember { mutableStateOf(true) }
//
//    LaunchedEffect(hasMoreResultsToLoad) {
//        if (!hasMoreResultsToLoad && previousHasMoreResults.value && pokemonList.size != 0) {
//            Toast.makeText(context, "No more Pokémon to load!", Toast.LENGTH_SHORT).show()
//        }
//        previousHasMoreResults.value = hasMoreResultsToLoad
//    }

    LaunchedEffect(Unit) {
        viewModel.initialSearchIfNeeded()
    }

    LaunchedEffect(pokemonList.size) {
        if (!isLoading && viewModel.animateToTop) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }

        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index != null && index >= pokemonList.size - 3 && !isLoading && hasMoreResultsToLoad) {
                    viewModel.loadMorePokemon()
                }
            }
    }

    Scaffold(
        topBar = {
            AppBar()
        }
    ) { paddingValues ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .padding(Dimens.contentPadding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.textFieldValue,
                    onValueChange = { viewModel.onTextChanged(it) },
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "Search Pokémon") }
                )

                Spacer(modifier = Modifier.width(Dimens.textFileSpinnerMargin))

                var expanded by remember { mutableStateOf(false) }
                DropdownWithButton(
                    expanded = expanded,
                    text = viewModel.selectedType,
                    onExpandedChange = { expanded = it},
                    onItemSelect = { type ->
                        viewModel.onTypeSelected(type)
                        expanded = false
                    }
                )
            }
            
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Dimens.pokemonListColumnsMinSize),
                state = listState,
                contentPadding = PaddingValues(Dimens.pokemonListPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.pokemonListItemsVerticalMargin),
                horizontalArrangement = Arrangement.spacedBy(Dimens.pokemonListItemsHeightMargin),
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading && pokemonList.isEmpty()) {
                    items(10) {
                        PokemonCardPlaceholder()
                    }
                } else {
                    items(pokemonList) {pokemon ->
                        PokemonCard(pokemon) {
                            val pokemonJson = Json.encodeToString(pokemon)
                            val encodedPokemonJson = Uri.encode(pokemonJson)
                            navController.navigate("${ScreenEndpoints.POKEMON_DETAILS_SCREEN}/$encodedPokemonJson")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showDialog = false },
            confirmButton = {
                Button(onClick = { viewModel.showDialog = false }) {
                    Text(text = stringResource(id = R.string.ok_label))
                }
            },
            text = { Text(viewModel.dialogMessage) }
        )
    }
}