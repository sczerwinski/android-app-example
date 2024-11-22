package it.czerwinski.examples.compose.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.czerwinski.examples.compose.R
import it.czerwinski.examples.compose.model.Item
import it.czerwinski.examples.compose.ui.common.ProgressScreen
import it.czerwinski.examples.compose.ui.common.TopBar
import it.czerwinski.examples.compose.ui.theme.ComposeExampleTheme
import it.czerwinski.examples.compose.vm.DetailsViewModel

@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    arguments: Bundle?,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val id = arguments?.getLong("id")

    val item by viewModel.item.collectAsState(initial = null)
    val isProgress by viewModel.isProgress.collectAsState(initial = false)

    LaunchedEffect(key1 = id) {
        if (id != null) {
            viewModel.loadItem(id)
        }
    }

    DetailsScreenContent(
        item = item,
        onBackClick = navHostController::popBackStack,
        onRefreshClick = { if (id != null) viewModel.loadItem(id) },
        isProgress = isProgress
    )
}

@Composable
fun DetailsScreenContent(
    item: Item?,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    isProgress: Boolean
) {
    if (isProgress) {
        ProgressScreen()
    } else {
        DetailsScreenLayout(
            item = item,
            onBackClick = onBackClick,
            onRefreshClick = onRefreshClick
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsScreenLayout(
    item: Item?,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title = item?.name ?: stringResource(id = R.string.no_item),
                onBackClick = onBackClick,
                onRefreshClick = onRefreshClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(ScreenPadding)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = ScreenArrangement,
            horizontalAlignment = Alignment.Start
        ) {
            if (item != null) {
                ItemDescription(item)

                // Some more content for scrolling:
                repeat(LOREM_IPSUM_COUNT) {
                    LoremIpsum(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun ItemDescription(item: Item) {
    Text(
        text = item.description,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun LoremIpsum(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.lorem_ipsum),
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium
    )
}

private val ScreenPadding = PaddingValues(all = 16.dp)
private val ScreenArrangement = Arrangement.spacedBy(12.dp)

private const val LOREM_IPSUM_COUNT = 4

@Preview
@Composable
fun DetailsScreenPreview() {
    ComposeExampleTheme {
        DetailsScreenContent(
            item = Item(id = 2L, name = "Item 3", description = "Description of item 3"),
            onBackClick = {},
            onRefreshClick = {},
            isProgress = false
        )
    }
}

@Preview
@Composable
fun DetailsScreenPreviewNoItem() {
    ComposeExampleTheme {
        DetailsScreenContent(
            item = null,
            onBackClick = {},
            onRefreshClick = {},
            isProgress = false
        )
    }
}

@Preview
@Composable
fun DetailsScreenPreviewProgress() {
    ComposeExampleTheme {
        DetailsScreenContent(
            item = Item(id = 2L, name = "Item 3", description = "Description of item 3"),
            onBackClick = {},
            onRefreshClick = {},
            isProgress = true
        )
    }
}
