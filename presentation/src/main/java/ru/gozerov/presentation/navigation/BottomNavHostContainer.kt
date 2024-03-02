package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.gozerov.presentation.screens.account.AccountScreen
import ru.gozerov.presentation.screens.tag_list.TagListScreen
import ru.gozerov.presentation.screens.tag_map.TagMapScreen
import ru.gozerov.presentation.screens.tag_map.TagMapViewModel

@Composable
fun BottomNavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBarItem.TagMap.route,
        builder = {
            composable(BottomNavBarItem.TagMap.route) {
                val tagViewModel = hiltViewModel<TagMapViewModel>()
                TagMapScreen(tagViewModel)
            }
            composable(BottomNavBarItem.TagList.route) {
                TagListScreen()
            }
            composable(BottomNavBarItem.Account.route) {
                AccountScreen()
            }
        }
    )

}