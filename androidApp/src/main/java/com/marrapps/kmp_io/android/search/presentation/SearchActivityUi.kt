package com.marrapps.kmp_io.android.search.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.marrapps.kmp_io.android.MyApplicationTheme
import com.marrapps.kmp_io.android.R
import com.marrapps.kmp_io.android.search.domain.regions
import com.marrapps.kmp_io.domain.model.Character
import com.marrapps.kmp_io.domain.model.ScoreDisplay
import org.koin.compose.viewmodel.koinViewModel

private const val NAME_MAX_LENGTH = 12
private const val SERVER_MAX_LENGTH = 18

@Composable
fun SearchView(searchViewModel: SearchViewModel = koinViewModel()) {
    val searchUiState by searchViewModel.uiState.collectAsState()
    val padding = 16.dp
    Column(
        Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.size(padding))
        NameTextField(searchViewModel::saveNameInput)
        Spacer(Modifier.size(padding))
        RegionTextField(searchViewModel::saveRegionInput)
        Spacer(Modifier.size(padding))
        ServerTextField(searchUiState.serverList, searchViewModel::saveServerInput)
        Spacer(Modifier.size(padding))
        Button(
            onClick = searchViewModel::onSearchButtonClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.search_button_label))
        }
    }
}

@Composable
fun NameTextField(onNameInput: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
            if (newText.text.length <= NAME_MAX_LENGTH) {
                text = newText
                onNameInput(newText.text)
            }
        },
        label = { Text(text = stringResource(R.string.character_name_label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun RegionTextField(onRegionInput: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text(text = stringResource(R.string.region_text_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.arrow_drop_down_24),
                    contentDescription = null
                )
            }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    expanded = !expanded
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            regions.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        text = TextFieldValue(it)
                        expanded = false
                        onRegionInput(it)
                    }
                )
            }
        }
    }
}

@Composable
fun ServerTextField(serverList: List<String>, onServerInput: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        TextField(
            enabled = serverList.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newText ->
                if (newText.text.length <= SERVER_MAX_LENGTH) {
                    text = newText
                    onServerInput(newText.text)
                }

                if (newText.text.isNotEmpty())
                    expanded = true
            },
            label = { Text(text = stringResource(R.string.server_text_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            serverList.filter {
                it.lowercase().startsWith(text.text.lowercase())
            }.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        text = TextFieldValue(it)
                        expanded = false
                        onServerInput(it)
                    }
                )
            }
        }
    }
}

@Composable
fun ResultView(searchViewModel: SearchViewModel = koinViewModel()) {
    val searchUiState by searchViewModel.resultState.collectAsState()

    with(searchUiState) {
        when (this) {
            is SearchViewModel.SearchCharacterState.Empty -> {
                // no composition, hide view
            }

            is SearchViewModel.SearchCharacterState.Loading -> {
                LoadingView()
            }

            is SearchViewModel.SearchCharacterState.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.search_error_message),
                    Toast.LENGTH_LONG
                ).show()
            }

            is SearchViewModel.SearchCharacterState.Success -> {
                CharacterDataView(this.character)
            }
        }
    }
}

@Composable
fun ScoreView(scoreDisplay: ScoreDisplay) {
    Row {
        Text(text = stringResource(R.string.score_role_label, scoreDisplay.role))
        Spacer(Modifier.size(4.dp))
        Text(
            text = scoreDisplay.score.toString(),
            color = Color(scoreDisplay.color.toColorInt())
        )
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterDataView(character: Character) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row {
            AsyncImage(
                model = character.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(text = character.name)
                Row {
                    Text(text = character.race)
                    Spacer(Modifier.size(4.dp))
                    Text(text = character.charClass)
                }
                Text(text = character.spec)
            }
        }
        Spacer(Modifier.size(32.dp))
        Text(text = stringResource(R.string.scores_label))
        Spacer(Modifier.size(8.dp))

        character.scores.forEach {
            ScoreView(it)
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            SearchView(viewModel())
            ResultView(viewModel())
        }
    }
}
