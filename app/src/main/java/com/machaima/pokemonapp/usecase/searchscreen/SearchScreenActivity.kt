package com.machaima.pokemonapp.usecase.searchscreen

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.machaima.pokemonapp.navigation.PokemonNavGraph
import com.machaima.pokemonapp.ui.theme.PokemonAppTheme
import com.machaima.pokemonapp.usecase.searchscreen.ui.PokemonScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                PokemonNavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonAppTheme {
        PokemonNavGraph()
    }
}