package com.example.studychat

import com.example.studychat.ui.page.UserManagePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studychat.ui.App
import com.example.studychat.ui.page.ChatPage
import com.example.studychat.ui.theme.StudyChatTheme
import com.example.studychat.utils.PreferenceUtils


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        PreferenceUtils.init(this)
        setContent {
            StudyChatTheme {
                val navController = rememberNavController()
                val isLogin = PreferenceUtils.getBoolean("isLogin",false)

                NavHost(
                    navController = navController,
                    startDestination = if (isLogin) "main" else "login"
                ) {
                    // 登录
                    composable("login") {
                        UserManagePage(
                            onLoginSuccess = {
                                PreferenceUtils.putBoolean("isLogin", true)
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("main") {
                        App(navController)
                    }
                    composable("chat") {
                        ChatPage(navController)
                    }
                }
            }
        }
    }
}
