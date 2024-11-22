package it.czerwinski.examples.compose.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) { contentPadding ->
        ProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Preview
@Composable
fun ProgressScreenPreview() {
    ProgressScreen()
}
