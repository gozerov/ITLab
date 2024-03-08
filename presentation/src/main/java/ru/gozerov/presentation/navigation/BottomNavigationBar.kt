package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun BottomNavigationBar(navController: NavController, contentPaddingValues: PaddingValues) {
    BottomNavigation(
        modifier = Modifier.padding(bottom = contentPaddingValues.calculateBottomPadding()),
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
                        tint = if (isSelected) ITLabTheme.colors.tintColor else ITLabTheme.colors.primaryText
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.labelId),
                        color = if (isSelected) ITLabTheme.colors.tintColor else ITLabTheme.colors.primaryText
                    )
                },
                selected = isSelected,
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