package com.ecoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ecoapp.R
import com.ecoapp.presentation.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsBottomSheet(
    onDismissRequest: () -> Unit,
    itemCounts: List<Pair<String, Int>>,
    topCharacters: List<Pair<Char, Int>>,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.sheet_corner_radius),
            topEnd = dimensionResource(id = R.dimen.sheet_corner_radius)
        ),
        contentColor = Color.White // Ensures that the content is visible if background is dark
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple40)
                .padding(dimensionResource(id = R.dimen.sheet_padding))
        ) {
            // Item Counts Section with Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.item_counts_title),
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.section_margin))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.section_margin)))
                    if (itemCounts.isNotEmpty()) {
                        itemCounts.forEach { (item, count) ->
                            Text(
                                text = stringResource(id = R.string.item_count_format, item, count),
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.section_margin))
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(id = R.string.no_data_found),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.section_margin))
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                  ,
                thickness = dimensionResource(id = R.dimen.thickness),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.section_margin)))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.top_characters_title),
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.section_margin))
                )
                if (topCharacters.isNotEmpty()) {
                    topCharacters.forEach { (char, count) ->
                        Text(
                            text = stringResource(id = R.string.top_character_format, char, count),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.item_margin))
                        )
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.no_data_found),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.item_margin))
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.section_margin)))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewStatsBottomSheet() {

    StatsBottomSheet(
        onDismissRequest = {},
        itemCounts = listOf(
            "Animals" to 5,
            "Birds" to 3
        ),
        topCharacters = listOf(
            'A' to 10,
            'B' to 8
        ),
        rememberModalBottomSheetState(true)
    )
}
