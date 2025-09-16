package com.example.studychat.ui

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studychat.ui.page.ChatListPage
import com.example.studychat.ui.page.ContactPage
import com.example.studychat.ui.page.SelfPage
import com.example.studychat.utils.PreferenceUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController){
    val innerNavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = Destination.ChatListPage
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
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
    ){ innerPadding ->
        AppNavHost(
            navController = innerNavController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            onLogout = {
                PreferenceUtils.putBoolean("isLogin", false)
                navController.navigate("login"){
                    popUpTo("main"){ inclusive = true}
                }
            }
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
){
    NavHost(
        navController,
        startDestination = startDestination.route,
    ){
        Destination.entries.forEach{ destination ->
            composable(destination.route){
                when(destination){
                    Destination.ChatListPage -> ChatListPage()
                    Destination.ContactPage -> ContactPage()
                    Destination.SelfPage -> SelfPage(onLogout)
                }
            }
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
