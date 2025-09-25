package com.example.studychat.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studychat.R
import com.example.studychat.ui.page.ChatListPage
import com.example.studychat.ui.page.ChatPage
import com.example.studychat.ui.page.ContactPage
import com.example.studychat.ui.page.SelfPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController) {
    val innerNavController = rememberNavController()

    val item = listOf(
        BottomNavItem("聊天", Icons.Filled.Menu, Icons.Outlined.Menu, "ChatList"),
        BottomNavItem("联系人", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, "Contact"),
        BottomNavItem("我的", Icons.Filled.Person, Icons.Outlined.Person, "Self"),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                item.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            innerNavController.navigate(item.route) {
                                popUpTo(innerNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                if (currentRoute == item.route) item.selectIcon else item.unselectIcon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(item.label)
                        },
                        alwaysShowLabel = false

                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
            ),
            navController = innerNavController,
            startDestination = item.first().route
        ) {
            composable("ChatList") { ChatListPage(navController) }
            composable("Contact") { ContactPage(navController) }
            composable("Self") { SelfPage(navController,) }
        }
    }

}
data class BottomNavItem(
    val label: String,
    val selectIcon: ImageVector,
    val unselectIcon: ImageVector,
    val route: String
)