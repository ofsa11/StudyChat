package com.example.studychat.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import com.example.studychat.R
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studychat.ui.page.ChatListPage
import com.example.studychat.ui.page.ChatPage
import com.example.studychat.ui.page.ContactPage
import com.example.studychat.ui.page.SelfPage
import com.example.studychat.utils.PreferenceUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController){
    val innerNavController = rememberNavController()
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val startDestination = Destination.ChatListPage
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val currentRoute = navBackStackEntry?.destination?.route
    val noBottomBarRoutes = listOf("chat")//不需要底部导航栏的界面
    val showBottomBar = currentRoute !in noBottomBarRoutes

    AnimatedContent(
        targetState = showBottomBar,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        },
        label = "ScaffoldContent"
    ) {targetShowBottomBar ->
        Scaffold (
            bottomBar = {
                if(targetShowBottomBar)
                {
                    BottomAppBar {
                        NavigationBar {
                            Destination.entries.forEachIndexed { index,destination ->
                                NavigationBarItem(
                                    selected = selectedDestination == index,
                                    onClick = {
                                        innerNavController.navigate(destination.route){
                                            popUpTo(innerNavController.graph.startDestinationId){ saveState = true}
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        selectedDestination = index
                                    },
                                    icon = {
                                        Icon(painter = painterResource(destination.icon), contentDescription = destination.contentDescription)
                                    },
                                    label = { Text(destination.label) }
                                )
                            }
                        }
                    }
                }
            }
        ){ innerPadding ->
            AppNavHost(
                navController = innerNavController,
                startDestination = startDestination,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = if (targetShowBottomBar) innerPadding.calculateBottomPadding() else 0.dp
                ),
                onLogout = {
                    PreferenceUtils.putBoolean("isLogin", false)
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier,
    onLogout: () -> Unit
){
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier,
        enterTransition = { slideInHorizontally(tween()) { it } },
        exitTransition = { slideOutHorizontally(tween()) { -it } + fadeOut(tween()) },
        popEnterTransition = { slideInHorizontally(tween()) { -it } },
        popExitTransition = { slideOutHorizontally(tween()) { it } }
    ){
        Destination.entries.forEach{ destination ->
            composable(destination.route){
                when(destination){
                    Destination.ChatListPage -> ChatListPage(navController)
                    Destination.ContactPage -> ContactPage()
                    Destination.SelfPage -> SelfPage(onLogout)
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
