@file:OptIn(ExperimentalMaterial3Api::class)

package it.czerwinski.examples.compose.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import it.czerwinski.examples.compose.R

@Composable
fun TopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onRefreshClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    MediumTopAppBar(
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            if (onBackClick != null) {
                TopBarBackButton(onBackClick = onBackClick)
            }
        },
        actions = {
            if (onRefreshClick != null) {
                TopBarRefreshButton(onRefreshClick = onRefreshClick)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarBackButton(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.action_back)
        )
    }
}

@Composable
private fun TopBarRefreshButton(onRefreshClick: () -> Unit) {
    IconButton(onClick = onRefreshClick) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(id = R.string.action_refresh)
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(title = "Top Bar Title")
}

@Preview
@Composable
fun TopBarPreviewWithBackButton() {
    TopBar(
        title = "Top Bar Title",
        onBackClick = {}
    )
}

@Preview
@Composable
fun TopBarPreviewWithRefreshButton() {
    TopBar(
        title = "Top Bar Title",
        onRefreshClick = {}
    )
}

@Preview
@Composable
fun TopBarPreviewWithBothButtons() {
    TopBar(
        title = "Top Bar Title",
        onBackClick = {},
        onRefreshClick = {}
    )
}
