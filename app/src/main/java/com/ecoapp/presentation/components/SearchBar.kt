package com.ecoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ecoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoSearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .border(dimensionResource(id = R.dimen.thickness), Color.Gray, MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.card_margin)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon_description), // Use string resource
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.card_margin)),
                tint = Color.Gray
            )
            TextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_placeholder), // Use string resource
                        color = Color.Gray,
                        fontSize = dimensionResource(id = R.dimen.font_size).value.sp
                    )
                },
                trailingIcon = {
                    if (query.text.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.clear_query_description), // Use string resource
                                tint = Color.Gray
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewEcoSearchBar() {
    EcoSearchBar(
        query = TextFieldValue(""),
        onQueryChange = {},
        onClearQuery = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.search_bar_height))
            .padding(dimensionResource(id = R.dimen.section_margin))
    )
}
