package it.czerwinski.examples.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = "/",
        modifier = modifier
    ) {
        composable(route = "/") {
            ListScreen(navHostController = navHostController)
        }

        composable(
            route = "/item/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            DetailsScreen(
                navHostController = navHostController,
                arguments = backStackEntry.arguments
            )
        }
    }
}
