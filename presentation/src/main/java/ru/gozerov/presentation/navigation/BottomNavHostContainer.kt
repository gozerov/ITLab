package ru.gozerov.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.gozerov.presentation.screens.account.AccountScreen
import ru.gozerov.presentation.screens.tag_list.TagListScreen
import ru.gozerov.presentation.screens.tag_map.TagMapScreen

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
                TagMapScreen()
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