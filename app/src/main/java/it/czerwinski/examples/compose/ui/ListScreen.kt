package it.czerwinski.examples.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import it.czerwinski.examples.compose.ui.common.ProgressIndicator
import it.czerwinski.examples.compose.ui.common.TopBar
import it.czerwinski.examples.compose.ui.theme.ComposeExampleTheme
import it.czerwinski.examples.compose.vm.ListViewModel

@Composable
fun ListScreen(
    navHostController: NavHostController,
    viewModel: ListViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    val isProgress by viewModel.isProgress.collectAsState(initial = false)

    ListScreenContent(
        items = items,
        onItemClick = { item ->
            navHostController.navigate(route = "/item/${item.id}")
        },
        onRefreshClick = { viewModel.loadItems() },
        isProgress = isProgress
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ListScreenContent(
    items: List<Item>,
    onItemClick: (Item) -> Unit,
    onRefreshClick: () -> Unit,
    isProgress: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                onRefreshClick = onRefreshClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(ItemsPadding),
            verticalArrangement = Arrangement.spacedBy(ItemsSpacing),
            horizontalAlignment = Alignment.Start
        ) {
            when {
                isProgress -> listScreenProgressItem()
                items.isEmpty() -> listScreenPlaceholderItem()
                else -> listScreenItems(
                    items = items,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

private fun LazyListScope.listScreenProgressItem() {
    item {
        ProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

private fun LazyListScope.listScreenPlaceholderItem() {
    item {
        Text(
            text = stringResource(id = R.string.empty_list_placeholder),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun LazyListScope.listScreenItems(
    items: List<Item>,
    onItemClick: (Item) -> Unit
) {
    items(
        items = items,
        key = { item -> item.id }
    ) { item ->
        ListScreenItem(
            item = item,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun ListScreenItem(
    item: Item,
    onItemClick: (Item) -> Unit
) {
    TextButton(
        onClick = { onItemClick(item) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = item.name,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private val ItemsPadding = PaddingValues(all = 16.dp)
private val ItemsSpacing = 8.dp

@Preview
@Composable
fun ListScreenPreview() {
    ComposeExampleTheme {
        ListScreenContent(
            items = listOf(
                Item(id = 0L, name = "Item 1", description = "Description of item 1"),
                Item(id = 1L, name = "Item 2", description = "Description of item 2"),
                Item(id = 2L, name = "Item 3", description = "Description of item 3"),
                Item(id = 3L, name = "Item 4", description = "Description of item 4")
            ),
            onItemClick = {},
            onRefreshClick = {},
            isProgress = false
        )
    }
}

@Preview
@Composable
fun ListScreenPreviewEmpty() {
    ComposeExampleTheme {
        ListScreenContent(
            items = emptyList(),
            onItemClick = {},
            onRefreshClick = {},
            isProgress = false
        )
    }
}

@Preview
@Composable
fun ListScreenPreviewProgress() {
    ComposeExampleTheme {
        ListScreenContent(
            items = listOf(
                Item(id = 0L, name = "Item 1", description = "Description of item 1"),
                Item(id = 1L, name = "Item 2", description = "Description of item 2"),
                Item(id = 2L, name = "Item 3", description = "Description of item 3"),
                Item(id = 3L, name = "Item 4", description = "Description of item 4")
            ),
            onItemClick = {},
            onRefreshClick = {},
            isProgress = true
        )
    }
}
