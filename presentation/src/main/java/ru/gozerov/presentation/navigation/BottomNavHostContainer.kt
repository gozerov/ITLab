package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.account.AccountScreen
import ru.gozerov.presentation.screens.account.AccountViewModel
import ru.gozerov.presentation.screens.shared.enterAnimation
import ru.gozerov.presentation.screens.shared.exitAnimation
import ru.gozerov.presentation.screens.tag_list.details.TagDetailsScreen
import ru.gozerov.presentation.screens.tag_list.details.TagDetailsViewModel
import ru.gozerov.presentation.screens.tag_list.list.TagListScreen
import ru.gozerov.presentation.screens.tag_list.list.TagListViewModel
import ru.gozerov.presentation.screens.tag_map.TagMapScreen
import ru.gozerov.presentation.screens.tag_map.TagMapViewModel

@Composable
fun BottomNavHostContainer(
    navController: NavHostController,
    rootNavController: NavController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBarItem.TagMap.route,
        builder = {
            composable(
                route = BottomNavBarItem.TagMap.route,
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                val tagMapViewModel = hiltViewModel<TagMapViewModel>()
                TagMapScreen(tagMapViewModel)
            }
            navigation(Screen.TagList.route, BottomNavBarItem.TagListFlow.route) {
                composable(
                    route = Screen.TagList.route,
                    enterTransition = { enterAnimation() },
                    exitTransition = { exitAnimation() }
                ) {
                    val tagListViewModel = hiltViewModel<TagListViewModel>()
                    TagListScreen(navController, tagListViewModel, padding)
                }
                composable(
                    route = Screen.TagDetails.route,
                    enterTransition = { enterAnimation() },
                    exitTransition = { exitAnimation() }
                ) { _ ->
                    val tagDetailsViewModel = hiltViewModel<TagDetailsViewModel>()
                    val tag =
                        navController.previousBackStackEntry?.savedStateHandle?.get<Tag>("tag")
                    tag?.let {
                        TagDetailsScreen(
                            paddingValues = padding,
                            viewModel = tagDetailsViewModel,
                            navController = navController,
                            tag = tag
                        )
                    }
                }
            }
            composable(
                route = BottomNavBarItem.Account.route,
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }) {
                val accountViewModel = hiltViewModel<AccountViewModel>()
                AccountScreen(accountViewModel, padding, rootNavController)
            }
        }
    )

}