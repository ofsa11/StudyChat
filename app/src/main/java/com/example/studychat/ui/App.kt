package com.example.studychat.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
//    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
//    val startDestination = Destination.ChatListPage
//    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
//    val currentRoute = navBackStackEntry?.destination?.route
//    val noBottomBarRoutes = listOf("chat")//不需要底部导航栏的界面
//    val showBottomBar = currentRoute !in noBottomBarRoutes


    val item = listOf(
        BottomNavItem("聊天", Icons.Filled.Menu, Icons.Outlined.Menu, "ChatList"),
        BottomNavItem("联系人", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, "Contact"),
        BottomNavItem("我的", Icons.Filled.Person, Icons.Outlined.Person, "Self"),
    )
//    AnimatedContent(
//        targetState = showBottomBar,
//        transitionSpec = {
//            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
//        },
//        label = "ScaffoldContent"
//    ) { targetShowBottomBar ->
//        Scaffold(
//            bottomBar = {
//                if (targetShowBottomBar) {
//                    BottomAppBar {
//                        NavigationBar {
//                            Destination.entries.forEachIndexed { index, destination ->
//                                NavigationBarItem(
//                                    selected = selectedDestination == index,
//                                    onClick = {
//                                        innerNavController.navigate(destination.route) {
//                                            popUpTo(innerNavController.graph.startDestinationId) {
//                                                saveState = true
//                                            }
//                                            launchSingleTop = true
//                                            restoreState = true
//                                        }
//                                        selectedDestination = index
//                                    },
//                                    icon = {
//                                        Icon(
//                                            painter = painterResource(destination.icon),
//                                            contentDescription = destination.contentDescription
//                                        )
//                                    },
//                                    label = { Text(destination.label) }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        ) { innerPadding ->
//            AppNavHost(
//                navController = innerNavController,
//                startDestination = startDestination,
//                modifier = Modifier.padding(
//                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
//                    bottom = if (targetShowBottomBar) innerPadding.calculateBottomPadding() else 0.dp
//                ),
//                onLogout = {
//                    PreferenceUtils.putBoolean("isLogin", false)
//                    navController.navigate("login") {
//                        popUpTo("main") { inclusive = true }
//                    }
//                }
//            )
//        }
//    }

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
    ) {
        NavHost(
            navController = innerNavController,
            startDestination = item.first().route
        ) {
            composable("ChatList") { ChatListPage(navController) }
            composable("Contact") { ContactPage(navController) }
            composable("Self") { SelfPage(navController,) }
        }
    }

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier,
    onLogout: () -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier,
        enterTransition = { slideInHorizontally(tween()) { it } },
        exitTransition = { slideOutHorizontally(tween()) { -it } + fadeOut(tween()) },
        popEnterTransition = { slideInHorizontally(tween()) { -it } },
        popExitTransition = { slideOutHorizontally(tween()) { it } }
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.ChatListPage -> ChatListPage(navController)
                    Destination.ContactPage -> ContactPage(navController)
                    Destination.SelfPage -> SelfPage(navController)
                }
            }
        }

        composable("chat") {
            ChatPage(navController)
        }

    }
}

enum class Destination(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    ChatListPage("chatlist", "ChatList", R.drawable.ic_chatlist, "ChatList"),
    ContactPage("contact", "Contact", R.drawable.ic_contact, "Contact"),
    SelfPage("self", "Self", R.drawable.ic_myself, "Self"),
}


data class BottomNavItem(
    val label: String,
    val selectIcon: ImageVector,
    val unselectIcon: ImageVector,
    val route: String
)