package it.czerwinski.examples.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.czerwinski.examples.compose.ui.MainNavHost
import it.czerwinski.examples.compose.ui.theme.ComposeExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExampleTheme {
                MainNavHost(
                    navHostController = rememberNavController(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
