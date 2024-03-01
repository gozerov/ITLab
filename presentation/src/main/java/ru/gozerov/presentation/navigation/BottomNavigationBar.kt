package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.padding(bottom = 48.dp),
        backgroundColor = ITLabTheme.colors.primaryBackground,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavBarItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.iconId),
                        contentDescription = null,
                        tint = if (isSelected) ITLabTheme.colors.tintColor else ITLabTheme.colors.navigationBarColor
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.labelId),
                        color = if (isSelected) ITLabTheme.colors.tintColor else ITLabTheme.colors.navigationBarColor
                    )
                },
                selected = isSelected,
                //colors = NavigationBarItemDefaults.colors(selectedTextColor = ITLabTheme.colors.actionColor),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}

/*
* NavigationBar(
        modifier = Modifier.height(56.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavBarItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                modifier = Modifier.height(48.dp),
                icon = {
                    Icon(painter = painterResource(screen.iconId), contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = screen.labelId))
                },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(selectedTextColor = ITLabTheme.colors.actionColor),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }*/

@Composable
fun NavItem() {

}