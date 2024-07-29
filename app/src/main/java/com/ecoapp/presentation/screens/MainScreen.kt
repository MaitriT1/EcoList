package com.ecoapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ecoapp.R
import com.ecoapp.data.model.EcoItem
import com.ecoapp.presentation.components.EcoItemDetail
import com.ecoapp.presentation.components.EcoSearchBar
import com.ecoapp.presentation.components.StatsBottomSheet
import com.ecoapp.presentation.theme.Purple40
import com.ecoapp.presentation.viewmodel.MainViewModel
import com.google.accompanist.pager.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.dimensionResource

@OptIn(
    ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(innerPadding: PaddingValues, viewModel: MainViewModel = hiltViewModel()) {

    val ecoItems by viewModel.ecoItems.collectAsState()
    val filteredItems by viewModel.filteredItems.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val pagerState = rememberPagerState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        query = TextFieldValue("")
        keyboardController?.hide()
        viewModel.updateFilteredItems(pagerState.currentPage, query.text)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.section_margin)))
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    if (ecoItems.isNotEmpty()) {
                        DisplayEcoItems(items = ecoItems, pagerState = pagerState)
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.section_margin)))
                }

                stickyHeader {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_top_10)))
                    EcoSearchBar(
                        query = query,
                        onQueryChange = { newQuery ->
                            query = newQuery
                            viewModel.updateFilteredItems(pagerState.currentPage, newQuery.text)
                        },
                        onClearQuery = {
                            query = TextFieldValue("")
                            viewModel.updateFilteredItems(pagerState.currentPage, "")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.search_bar_height))
                            .padding(horizontal = dimensionResource(id = R.dimen.section_margin))
                    )
                }

                items(filteredItems) { item ->
                    EcoItemDetail(item = item)
                }
                if (filteredItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(id = R.dimen.section_margin)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_data_found),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(id = R.dimen.section_margin)),
                containerColor = Purple40,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }


        if (showBottomSheet) {
            StatsBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                itemCounts = viewModel.getItemCountsForPage(pagerState.currentPage),
                topCharacters = viewModel.getTopCharactersForPage(pagerState.currentPage),
                sheetState = rememberModalBottomSheetState(showBottomSheet)
            )
        }


    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DisplayEcoItems(items: List<EcoItem>, pagerState: PagerState) {
    Column {
        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.section_height))
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.section_margin))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.section_margin))) // Rounded corners
                    .background(Color.Black) // Background color
            ) {
                Image(
                    painter = painterResource(id = items[page].imageResId),
                    contentDescription = stringResource(id = R.string.image_content_description), // Use string resource
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(dimensionResource(id = R.dimen.section_margin)),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}







